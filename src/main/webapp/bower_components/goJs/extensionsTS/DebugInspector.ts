"use strict";
/*
*  Copyright (C) 1998-2017 by Northwoods Software Corporation. All Rights Reserved.
*/

import * as go from "../release/go";
import { ExtendedBrush } from "./ExtendedBrush";

/*
  DebugInspector code
  Two classes: DebugInspector and View
  An DebugInspector is a collection of Views

  Each property corresponds to its own View.
  Each View correspondingly has its own DOM elements.
  Views are also responsible for populating those DOM elements
  and committing information from them, when requested.
  Right now Views have three essential settable functions:
    initialize = Creates DOM. Called once.
    populate = Fills DOM, ie input.value = some value. Called each time the DebugInspector asks.
    use = Returns a value or values from the View's DOM inputs. DebugInspector solicits this when it wants to commit some values.

  The inspector adds the DOM elements of all Views to its master div (this.div), plus an accept button (or not).

  Finally the inspector has a collection of objects to inspect.
  For now this is only ever a single object.

  When the collection changes, the inspector passes on information
  to all of its Views so that they may re-populate their inputs.
*/

export class DebugInspector {
	private divid: string;
	private diagram: go.Diagram;
	private options: Object;
	private div: HTMLElement;
	private acceptResetDiv: HTMLDivElement;
	private chooser: HTMLSelectElement;
	private lastIndex: number;
	private viewName: HTMLElement;

	private inspectedProps = {};
	// assume coll is a single object for now TODO fix this,
	// which means looking at every use of this.collection
	private collection: go.GraphObject= null;
	private tracked = {}; // map of all tracked properties, set in showObjectProperties and used in rebuildViews

	private acceptButton: boolean;
	private resetButton: boolean;
	private acceptFocus: boolean;
	private whenSelected: boolean;
	private inspectPredicate: boolean;
	private propertyPredicate: boolean;
	private propertyNames: go.GraphObject;

	constructor(divid: string, diagram: go.Diagram, options: Object) {
		this.divid = divid;
		this.diagram = diagram;
		this.options = options;

		this.acceptButton = !!(<any>this.options)['acceptButton'];
		this.resetButton = !!(<any>this.options)['resetButton'];
		this.acceptFocus = !!(<any>this.options)['acceptFocus'];
		this.whenSelected = ((<any>this.options)['whenSelected'] === undefined) ? true : !!(<any>this.options)['whenSelected'];
		this.inspectPredicate = (<any>this.options)['inspectPredicate'] || null;
		this.propertyPredicate = (<any>this.options)['propertyPredicate'] || null;
		this.propertyNames = (<any>this.options)['propertyNames'] || null;

		/*
		<div id="divid" class="inspector-container">
			<select></select>
			<div class="inspector"></div>
			<div class="inspector-button-container"></div>
		</div>
		*/
		var div = document.getElementById(divid);
		div.className = "inspector-container";
		var chooser = document.createElement("select");
		div.appendChild(chooser);
		var mainDiv = document.createElement("div");
		mainDiv.className = "inspector";
		div.appendChild(mainDiv);
		var acceptResetDiv = document.createElement("div");
		acceptResetDiv.className = "inspector-button-container";
		div.appendChild(acceptResetDiv);

		this.div = mainDiv;
		this.acceptResetDiv = acceptResetDiv;
		this.chooser = chooser;
		this.lastIndex = 0;
		var self = this;
		function updateViews(e: go.ChangedEvent) {
			if (e.isTransactionFinished) self.rebuildViews();
		}
		if (this.diagram.addModelChangedListener) {
			// automatically reregisters listener when the Diagram.model is replaced
			this.diagram.addModelChangedListener(updateViews);
		} else {
			this.diagram.model.addChangedListener(updateViews);
		}

		if (this.whenSelected) {
			var pred = this.inspectPredicate;
			diagram.addDiagramListener('ChangedSelection', function (e) {
				var inspectedObjects: Array<go.GraphObject> = [];
				var selection = diagram.selection.first();
				if (selection === null) {
					self.toggleVisibility(false);
					return;
				}

				if (self.propertyNames !== null) {
					self.populatePropertyNames(inspectedObjects, pred, selection); // NYI
				} else {
					self.populateDefault(inspectedObjects, pred, selection);
				}

				chooser.innerHTML = '';
				for (var i = 0; i < inspectedObjects.length; i++) {
					var option = document.createElement('option');
					option.value = i.toString();
					option.text = inspectedObjects[i] instanceof go.GraphObject ? inspectedObjects[i].toString() : 'data';
					chooser.appendChild(option);
				}

				chooser.onchange = function () {
					var index = (this as any).selectedIndex;
					if (self.propertyNames !== null) {
						self.change(selection);
					} else {
						self.change(inspectedObjects[index]);
					}
					self.lastIndex = index;
				}
				self.toggleVisibility(true);

				var useLastIndex = self.lastIndex <= (inspectedObjects.length - 1);
				if (self.propertyNames !== null) {
					self.change(selection);
				} else {
					self.change(inspectedObjects[useLastIndex ? self.lastIndex : 0]);
				}
				if (useLastIndex) chooser.value = self.lastIndex.toString();
				// if the index was from the lastIndex than we need to update the chooser
			});
		}

		// accept focus option = commit when clicking away from the div:
		if (this.acceptFocus) {
			div.tabIndex = 3; // div must be allowed focus
			// must use focusout instead of blur - blur does not bubble,
			// so clicking on an <input> inside the div and then clicking away
			// will not fire an event with blur, and it will focusout
			div.addEventListener('focusout', function (e) {
				self.setAllProperties();
			});
		}

		this.toggleVisibility(false);
	} // end DebugInspector constructor

	// Populate the selection box with all GraphObjects inside a node, plus its part.data
	public populateDefault(graphObjects: Array<go.GraphObject>, pred: any, selection: go.Panel) {
		if (pred === null || pred(selection)) {
			graphObjects.push(selection);
		}
		var recurseDownPanels = function (element: go.GraphObject) {
			if (element instanceof go.Panel) {
				var elements = element.elements;
				while (elements.next()) {
					var val = elements.value;
					if (pred === null || pred(val)) {
						graphObjects.push(val);
					}
					recurseDownPanels(val);
				}
			}
		}
		recurseDownPanels(selection);
		if (pred === null || pred(selection.data)) {
			graphObjects.push(selection.data);
		}
	}

	// populate based on this.propertyNames
	public populatePropertyNames(graphObjects: Array<go.GraphObject>, pred: any, selection: go.Panel) {
		graphObjects.push(this.propertyNames);
	}

	public change(graphObject: go.GraphObject) {
		this.inspectedProps = {};
		this.setCollection(graphObject);
		this.showObjectProperties(graphObject);
	}

	public toggleVisibility(visible: boolean) {
		this.div.style.display = visible ? "block" : "none";
		this.acceptResetDiv.style.display = visible ? "block" : "none";
		// Hide the chooser if there's only one observed object being inspected
		this.chooser.style.display = (visible && this.chooser.options.length > 1) ? "block" : "none";
	}

	// coll = collection of objects (JavaScript Objects, GraphObjects, Parts, etc)
	// assumes coll is a single object for now
	public setCollection(coll: go.GraphObject) {
		this.collection = coll;
	}

	public rebuildViews() {
		// clear any old spectrum controls that were bound to old inputs
		var spectrums = document.getElementsByClassName("sp-container");
		while (spectrums.length !== 0) document.body.removeChild(spectrums[0]);
		var div = this.div;
		while (div.children.length !== 0) div.removeChild(div.children[0]);
		this.acceptResetDiv.innerHTML = '';
		var trackedList = Object.keys(this.tracked);
		for (var i = 0; i < trackedList.length; i++) {
			var section = DebugInspector.prototype.createSection(trackedList[i]);
			var inspectedProps = (<any>this.tracked)[trackedList[i]];
			for (var j = 0; j < inspectedProps.length; j++) {
				var propname = inspectedProps[j];
				var view = (<any>this.inspectedProps)[propname];
				if (!view || view.visible !== true) continue;

				section.appendChild(view.getDOM());
				var coll = this.collection;
				var group = view.dom;
				var label = group.label || group.children[0];
				// optionally, sanitize property name into something human readable
				var camelCaseConverter = function (str: string) {
					var words = str.match(/[A-Za-z0-9][a-z]*/g);
					words = words.filter(function (str: string) { // the checkbox is clear enough the property is a bool
						return str !== "is";
					});
					return words.map(function (str) {
						if (str.length === 0) return str;
						else if (str.length === 1) return str.toUpperCase();
						else return str.substring(0, 1).toUpperCase() + str.substring(1);
					}).join(" ");
				}
				label.textContent = propname; //camelCaseConverter(propname);

				var input = group.children[1];
				if (view.options.readOnly) {
					for (var k = 0; k < group.children.length; k++) {
						group.children[k].disabled = true;
					}
				}

				var type = view.getType();
				if (type === "Point" || type === "Size" || type === "Margin" || type === "Rect" || type == "Spot") {
					view.originalValue = view.getter(propname).copy();
				} else {
					var input;
					if (type === "Brush" || type === "boolean") input = group;
					else input = group.children[1];
					view.originalValue = input.value;
				}

				var val = "";
				if (coll !== null) val = view.getter(propname);
				view.inferredType = (typeof val); // number, boolean, function, object
				if (val === undefined) val = "";
				else if (val === "(null)") {
					view.nullCheckBox.checked = true;
				} else {
					if (view.nullCheckBox) view.nullCheckBox.checked = false;
				}
				view.populate(val);

			}
			div.appendChild(section);
		}

		var self = this;
		if (this.acceptButton) {
			this.buildButton("Accept", function () {
				self.setAllProperties();
				self.rebuildViews();
			});
		}

		if (this.resetButton) {
			this.buildButton("Reset", function () {
				self.rebuildViews();
			});
		}
	}

	public buildButton(text: string, clickFunction: EventListenerOrEventListenerObject) {
		var viewName = text + "View";
		if ((<any>this)[viewName] === undefined) {
			var button = document.createElement("input");
			button.type = "button";
			button.value = text;
			button.addEventListener("click", clickFunction);
			this.viewName = button;
		}
		this.acceptResetDiv.appendChild(this.viewName);
	}

	public setAllProperties() {
		var coll = this.collection;
		if (coll === null) return;
		var diagram = this.diagram;
		if (diagram === null) return;
		var inspectedProps = this.inspectedProps;
		var transactionName = 'set all properties';
		diagram.startTransaction(transactionName);
		for (var k in inspectedProps) {
			var view = (<any>inspectedProps)[k];
			if (!view || view.setter === null || view.options.readOnly) continue;
			// don't set values that have not changed
			var val = view.isNull() ? null : view.use(this);
			if (view.originalValue === val) continue;
			if (val instanceof go.Point || val instanceof go.Size || val instanceof go.Spot ||
				val instanceof go.Rect || val instanceof go.Margin) {
				if (view.originalValue.equals(val)) continue;
			}
			view.setter(diagram, view, k, val);
		}
		diagram.commitTransaction(transactionName);
	}

	public createProperty(propname: string, options: Object, object: go.GraphObject) {
		var pred = this.propertyPredicate;
		if (pred === null || (<any>pred)(object, propname)) {
			// assume propname to be unique, create it if it does not exist
			// right now this clobbers the old View if it existed before
			var inspectedProps = this.inspectedProps;
			var view = new View(object, options);
			(<any>inspectedProps)[propname] = view;
			view.name = propname;
		}
	}

	// create it if it doesn't exist, then append
	public appendProperty(propname: string, options: Object, object: go.GraphObject) {
		var inspectedProps = this.inspectedProps;
		this.createProperty(propname, options, object);
		if ((<any>inspectedProps)[propname]) {
			(<any>inspectedProps)[propname].visible = true;
		}
	}

	public hideAllProperties() {
		var inspectedProps = this.inspectedProps;
		for (var propname in inspectedProps) {
			(<any>inspectedProps)[propname].visible = false;
		}
	}

	// Show all the relevant properties for a given object
	// If the object is a GoJS object it will look at the Type._inspectedProperties and show those
	public showObjectProperties(obj: go.GraphObject) {
		this.hideAllProperties();
		if (obj === null) return; // no object to show
		this.tracked = {};
		// TODO: Allow arbitrary objects later, or objects where _inspectedProperties is not defined
		// this will go through them, ie Node Part Panel GraphObject (in that order)
		var proto = Object.getPrototypeOf(obj);


		if (this.propertyNames !== null) { // given list of property names
			var propertyNames = this.propertyNames;
			var node = this.collection as go.Node;
			for (var k in propertyNames) {
				if ((<any>go)[k] !== undefined && (<any>go)[k]['_inspectedProperties'] !== undefined) {
					// it's Node/Panel/GraphObject/etc, populate the names in the list
					var name = k;
					var section = DebugInspector.prototype.createSection(name);
					(<any>this.tracked)[name] = [];
					var props = (<any>propertyNames)[k];
					for (var i = 0; i < props.length; i++) {
						var prop = props[i];

						// Find property options
						var options = null;
						while (options === null && proto && proto.constructor !== Object) {
							if (proto.constructor._inspectedProperties[prop]) options = proto.constructor._inspectedProperties[prop];
							proto = Object.getPrototypeOf(proto);
						}
						(<any>this.tracked)[name].push(prop);
						this.appendProperty(prop, options, obj);
					}
				} else {
					// Is it a findObject "#name"? Test each and populate
					var name = k.substr(1); // remove the #
					var obj = node.findObject(name);
					if (obj === null) continue;
					var section = DebugInspector.prototype.createSection(name);
					(<any>this.tracked)[name] = [];
					var props = (<any>propertyNames)[k];
					for (var i = 0; i < props.length; i++) {
						var prop = props[i];
						(<any>this.tracked)[name].push(prop);
						this.appendProperty(prop, { /* empty options */ }, obj);
					}
				}
			}

		} else if (proto.constructor === Object) { // arbitrary JS Object (for the Part.data)
			var name = 'data';
			var section = DebugInspector.prototype.createSection(name);
			(<any>this.tracked)[name] = [];
			for (var _prop in obj) {
				var options: any = { setter: View.prototype.defaultDataSetter };
				if (prop === '__gohashid') {
					(<any>options)['type'] = "String";
					(<any>options)['readOnly'] = true;
				}
				(<any>this.tracked)[name].push(prop);
				this.appendProperty(prop, options, this.collection);
			}
		} else { // GoJS object
			while (proto && proto.constructor !== Object) {
				var props = proto.constructor._inspectedProperties;
				// toString often produces strings such as: Class("someInfo")#ID
				// We want the name to be just the Class.
				var name: string = (new proto.constructor().toString()).split('#')[0].split('(')[0];
				var section = DebugInspector.prototype.createSection(name);
				(<any>this.tracked)[name] = [];
				for (var x in props) {
					(<any>this.tracked)[name].push(x);
					this.appendProperty(x, props[x], this.collection);
				}
				proto = Object.getPrototypeOf(proto);
			}
		}
		this.rebuildViews();
	}


	// node, part, panel, graphobject properties
	public createSection(name: string): HTMLDivElement {
		var div = document.createElement("div");
		div.className = "inspector-section";
		var h3 = document.createElement("h3");
		h3.innerHTML = name + " Properties";
		div.appendChild(h3);
		return div;
	}
}

/**
* View
* object: usually the this.collection/Node
*/
export class View {
	private object: any;
	private options: any;
	public name: string;
	private getter: any;
	private setter: any;
	private inialized:boolean = false;
	private visible: boolean = true;
	private dom: any = null;
	private originalValue: number = null;
	private inferredType: any;
	private nullCheckBox: any;
	private inputs: any;

	constructor(object: any, options: any) {
		this.object = object;
		options = options || {};
		this.options = options;
		this.getter = options.getter || View.prototype.defaultGetter;
		this.setter = options.setter || View.prototype.defaultSetter;
	}

	public getDOM(): any {
		if (!this.inialized) {
			this.dom = this.initialize();
			this.inialized = true;
		}
		return this.dom;
	}

	public getType(): string {
		return this.options.type || this.inferredType;
	}

	public isNullable(): boolean {
		// check to see if the object was specifically set to nullable in the metadata
		if (this.options.nullable) return true;
		var type = this.getType();
		if ((<any>go)[type] !== undefined) {
			return !!(<any>go)[type].nullable;
		}
		return false; // not nullable by default
	}

	public isNull(): boolean {
		return this.isNullable() && (this.nullCheckBox && this.nullCheckBox.checked);
	}

	// Returns a div with a label or null checkbox
	public makeInspectorGroup(): HTMLDivElement {
		var div = (document.createElement("div") as any);
		div.className = "inspector-group";
		var label = document.createElement("label");
		label.textContent = "LABEL";
		label.className = "property-name";

		var appendElement = label;
		if (this.isNullable()) {
			div.className += " inspector-nullable";
			var span = document.createElement("span");
			span.appendChild(label);
			var nullLabel = document.createElement("label");
			var nullText = document.createElement("span");
			nullText.textContent = "Null?";
			var nullCheckBox = document.createElement("input");
			nullCheckBox.type = "checkbox";
			nullLabel.appendChild(nullCheckBox);
			nullLabel.appendChild(nullText);
			span.appendChild(nullLabel);
			appendElement = span as HTMLLabelElement;
			this.nullCheckBox = nullCheckBox;
		}
		div.appendChild(appendElement);
		div.label = label;
		return div;
	}

	public nFieldHTMLInit(fields: Array<string>): HTMLDivElement {
		var div = this.makeInspectorGroup();
		var colsPerRow = 2;
		var inputs = [];
		var row: Array<Node> = [];

		var populateRow = function () {
			var rowDiv = document.createElement("div");
			for (var i = 0; i < row.length; i++) {
				rowDiv.appendChild(row[i]);
			}
			rowDiv.className = "row";
			div.appendChild(rowDiv);
			row = [];
		}

		for (var i = 0; i < fields.length; i++) {
			var label = document.createElement("label");
			label.textContent = fields[i];
			label.className = "inspector-n-field-label";

			var input = document.createElement("input");
			input.className = "input-short";
			inputs.push(input);

			var cell = document.createElement("div");
			cell.className = "cell";
			cell.appendChild(label);
			cell.appendChild(input);
			row.push(cell);
			if (row.length % colsPerRow === 0) populateRow();
		}

		if (row.length !== 0) populateRow();

		this.inputs = inputs;
		return div;
	}

	public initialize(): HTMLDivElement {
		var dom = this.dom;
		var type = this.getType();
		var div: any = null;
		switch (type) {
			case "Enum":
				div = this.makeInspectorGroup();
				var select = document.createElement("select");
				div.appendChild(select);
				break;
			case "Margin":
				div = this.nFieldHTMLInit(["Top", "Right", "Bottom", "Left"]);
				break;
			case "Point":
				div = this.nFieldHTMLInit(["X", "Y"]);
				break;
			case "Rect":
				div = this.nFieldHTMLInit(["X", "Y", "Width", "Height"]);
				break;
			case "Size":
				div = this.nFieldHTMLInit(["Width", "Height"]);
				break;
			case "Spot":
				div = this.nFieldHTMLInit(["X", "Y", "Offset X", "Offset Y"]);
				var inputs = this.inputs;
				var select = document.createElement("select");

				var spots = [];
				var i = 0;
				var custom = document.createElement("option");
				custom.value = (-1).toString();
				custom.text = "Custom Spot";
				select.appendChild(custom);
				for (var k in go.Spot) {
					if ((<any>go.Spot)[k] instanceof go.Spot) {
						var option = document.createElement("option");
						option.value = i.toString();
						option.text = k;
						i++;
						select.appendChild(option);
					}
				}
				select.value = (-1).toString();
				select.div = div;
				div.dropdown = select;
				var view = this;
				select.onchange = function () {
					var selected = select[select.selectedIndex].text;
					if (selected === "Custom Spot") return;
					var spot = go.Spot.parse(selected);
					view.inputs[0].value = spot.x;
					view.inputs[1].value = spot.y;
					view.inputs[2].value = spot.offsetX;
					view.inputs[3].value = spot.offsetY;
				}
				div.selectIndex = div.children.length;
				div.appendChild(select);
				break;
			case "Brush":
				div = this.makeInspectorGroup();
				var brushTextField = document.createElement("input");
				brushTextField.type = "text";
				div.appendChild(brushTextField);
				break;
			case "boolean":
				div = this.makeInspectorGroup();
				var input = document.createElement("input");
				input.type = "checkbox";
				input.className = "inspector-boolean-control";
				var label = div.children[0];
				label.className = "make-inline";
				div.insertBefore(input, label);
				div.input = input;
				break;
			case "number":
			default:
				div = this.makeInspectorGroup();
				var input = document.createElement("input");
				input.type = "text";
				if (this.getType() === "number") input.className += " right-align";
				input.className += " input-long";
				div.appendChild(input);
				div.input = input;
				break;
		}
		return div;
	} // end view init

	public populate(val: any) {
		var dom = this.dom;
		var type = this.getType();
		switch (type) {
			case "Enum":
				var vals = this.options.enumValues;
				var select = this.dom.children[1];
				while (select.children.length !== 0) select.removeChild(select.children[0]);
				if (select.length === 0) {
					for (var i = 0; i < vals.length; i++) {
						var option = document.createElement("option");
						option.value = i.toString();
						// just show the second half ("Position" instead of "Panel.Position")
						option.innerHTML = vals[i].toString().split('.')[1];
						select.appendChild(option);
					}
				}
				var index = -1;
				for (var i = 0; i < select.length; i++) {
					if (select[i].text === val.toString().split('.')[1]) {
						index = i;
						break;
					}
				}
				select.value = index;
				break;
			case "Margin":
				this.nFieldHTMLPopulate([val.top, val.left, val.bottom, val.right]);
				break;
			case "Point":
				this.nFieldHTMLPopulate([val.x, val.y]);
				break;
			case "Rect":
				this.nFieldHTMLPopulate([val.x, val.y, val.width, val.height]);
				break;
			case "Size":
				this.nFieldHTMLPopulate([val.width, val.height]);
				break;
			case "Spot":
				if (val instanceof go.Spot) {
					// check to see if spot we have is equal to a predefined Spot
					var predefined = null;
					for (var k in go.Spot) {
						if ((<any>go.Spot)[k] instanceof go.Spot && (<any>go.Spot)[k].equals(val)) {
							predefined = k;
							break;
						}
					}
					var select = dom.dropdown;
					if (predefined !== null) {
						var index = -1;
						for (var i = 0; i < select.children.length; i++) {
							var child = select.children[i];
							if (child.text === predefined) {
								index = parseInt(child.value);
								break;
							}
						}
						select.value = index;
					}
				}
				if (typeof val === "string") val = go.Spot.parse(val);
				this.nFieldHTMLPopulate([val.x, val.y, val.offsetX, val.offsetY]);
				break;
			case "Brush":
				var checkbox = this.nullCheckBox;
				var brushTextField = dom.children[1];
				if (val === "(null)") val = "black"; //
				// val is either a String or a Brush. If it's a Brush, make it a String
				var selected: any = $(brushTextField);
				if (typeof val === "string" || val.type === go.Brush.Solid) {
					var colorString = val;
					if (val instanceof go.Brush) colorString = val.color;
					var options = {
						replacerClassName: 'spectrum-control',
						color: colorString,
						showAlpha: true,
						showInput: true,
						clickoutFiresChange: true,
						showButtons: false,
						preferredFormat: "hex"
					};
					selected.spectrum(options);
				} else {
					selected.spectrum("destroy");
				}
				if (val instanceof go.Brush) {
					val = ExtendedBrush.prototype.stringify(val);
				}
				brushTextField.value = val; // val is now a String
				break;
			case "boolean":
				var input = dom.children[0];
				input.checked = val;
				break;
			case "number":
				dom.children[1].value = val;
				break;
			default:
				dom.children[1].value = val;
				break;
		}
	} // end view populate

	public nFieldHTMLPopulate(arr: Array<any>) {
		var children = this.inputs;
		for (var i = 0; i < arr.length; i++) {
			children[i].value = arr[i];
		}
	}

	public use(inspector: any): any {
		var group = this.dom;
		var type = this.getType();
		var val = null;
		switch (type) {
			case "Enum":
				val = this.options.enumValues[group.children[1].selectedIndex];
				break;
			case "Margin":
				var obj = this.nFieldHTMLUse(4);
				val = new go.Margin(obj[0], obj[1], obj[2], obj[3]);
				break;
			case "Point":
				var obj = this.nFieldHTMLUse(2);
				val = new go.Point(obj[0], obj[1]);
				break;
			case "Rect":
				var obj = this.nFieldHTMLUse(4);
				val = new go.Rect(obj[0], obj[1], obj[2], obj[3]);
				break;
			case "Size":
				var obj = this.nFieldHTMLUse(2);
				val = new go.Size(obj[0], obj[1]);
				break;
			case "Spot":
				var obj = this.nFieldHTMLUse(4);
				var select = this.dom.children[this.dom.selectIndex];
				if (select.selectedIndex !== 0) {
					var spot = go.Spot.parse(select[select.selectedIndex].text);
					var equals = function (a: number, b: number) { return a === b || (isNaN(a) && isNaN(b)); }
					// prefedined spots have NaN in their fields. You can't pass NaN into the normal spot constructor.
					// instead of constructing a Spot, try to see if it equals the selected item and returned the prefedined spot
					if (equals(obj[0], spot.x) && equals(obj[1], spot.y) && equals(obj[2], spot.offsetX) && equals(obj[3], spot.offsetY))
						val = spot;
					// otherwise, build a new Spot and just return that
				}
				if (val === null) val = new go.Spot(obj[0], obj[1], obj[2], obj[3]);
				break;
			case "Brush":
				if (this.nullCheckBox.checked) {
					val = null;
				} else {
					var brushTextField = group.children[1].value;
					var part = inspector.collection;
					part = part.part;
					var brush = ExtendedBrush.prototype.parse(brushTextField, part.measuredBounds.width, part.measuredBounds.height);
					val = brush;
				}
				break;
			case "boolean":
				val = group.children[0].checked;
				break;
			case "number":
				val = parseFloat(group.input.value);
				break;
			default:
				val = group.input.value;
				break;
		}
		return val;
	} // end view use

	public nFieldHTMLUse(count: number): Array<number> {
		var obj = [];
		var children = this.inputs;
		for (var i = 0; i < children.length; i++) {
			obj[i] = parseFloat(children[i].value);
		}
		return obj;
	}

	public defaultGetter(propname: string): string {
		var val = this.object[propname];
		if (val === null) return "(null)";
		return val;
	}

	public defaultDataGetter(propname: string): string {
		return this.object[propname];
	}

	/**
	* @param {Diagram} diagram
	* @param {View} view
	* @param {GraphObject} obj
	* @param {string} propname
	* @param {string} value
	*/
	public defaultSetter(diagram: go.Diagram, view: View, propname: string, value: string) {
		var mytype = view.getType();
		var myvalue: number;
		if (mytype === "number") myvalue = parseFloat(value);

		this.object[propname] = myvalue;
	}

	/**
	* @param {Diagram} diagram
	* @param {View} view
	* @param {Object} data
	* @param {string} propname
	* @param {string} value
	*/
	public defaultDataSetter(diagram: go.Diagram, view: View, propname: string, value: string) {
		diagram.model.setDataProperty(this.object, propname, value);
	}
}

// properties are either null or an object
// potential object properties:
//   type: string ("Brush", "Enum", "number")
//   enumValues: array of strings (["go.GraphObject.Fill, go.GraphObject.Uniform"])
//   readOnly: boolean (false by default, if omitted)
//   nullable: a property with nullable set to true means that it can be null. If omitted, objects are assumed to not be nullable unless a specific type (ie, Brush/Margin/etc) permits it
(<any>go.GraphObject)["_inspectedProperties"] = ({
	"stretch": {
		type: "Enum",
		enumValues: [go.GraphObject.None, go.GraphObject.Fill, go.GraphObject.Horizontal, go.GraphObject.Vertical, go.GraphObject.Default],
		defaultValue: go.GraphObject.Default
	},
	"name": {
		type: "String",
		readOnly: true
	},
	"opacity": {
		type: "number",
		defaultValue: 1
	},
	"visible": {
		type: "boolean",
		defaultValue: true
	},
	"areaBackground": {
		type: "Brush",
		defaultValue: null
	},
	"background": {
		type: "Brush",
		defaultValue: null
	},
	"position": { type: "Point" },
	"scale": {
		type: "number",
		defaultValue: 1
	},
	"angle": {
		type: "number",
		defaultValue: 0
	},
	"desiredSize": { type: "Size" },
	"measuredBounds": {
		type: "Rect",
		readOnly: true
	},
	"naturalBounds": {
		type: "Rect",
		readOnly: true
	},
	"portId": {
		type: "string",
		nullable: true,
		defaultValue: null
	},
	"minSize": {
		type: "Size",
		defaultValue: new go.Size(0, 0)
	},
	"maxSize": {
		type: "Size",
		defaultValue: new go.Size(Infinity, Infinity)
	}
});

(<any>go.Shape)["_inspectedProperties"] = ({
	"fill": {
		type: "Brush",
		defaultValue: "black"
	},
	"stroke": {
		type: "Brush",
		defaultValue: "black"
	},
	"strokeWidth": {
		type: "number",
		defaultValue: 1.0
	},
	"strokeCap": {
		type: "String",
		defaultValue: "butt"
	},
	"strokeJoin": {
		type: "String",
		defaultValue: "miter"
	},
	"strokeMiterLimit": {
		type: "number",
		defaultValue: 10
	},

	/*
	TODO
	"strokeDashArray": {
		type: "Array",
		defaultValue: null
	},
	"strokeDashOffset": {
		type: "number",
		defaultValue: 0
	},
	*/
	"figure": {
		type: "String",
		defaultValue: "None"
	},
	"toArrow": {
		type: "String",
		defaultValue: "None"
	},
	"fromArrow": {
		type: "String",
		defaultValue: "None"
	},
	"geometryStretch": {
		type: "Enum",
		enumValues: [go.GraphObject.None, go.GraphObject.Fill, go.GraphObject.Uniform, go.GraphObject.Default],
		defaultValue: go.GraphObject.Default
	}
});

(<any>go.TextBlock)["_inspectedProperties"] = ({
	"font": {
		type: "String",
		defaultValue: "10px sans-serif"
	},
	"text": { type: "String" },
	"textAlign": {
		type: "String",
		defaultValue: "start"
	},
	"isMultiline": {
		type: "boolean",
		defaultValue: true
	},
	"isUnderline": {
		type: "boolean",
		defaultValue: false
	},
	"isStrikethrough": {
		type: "boolean",
		defaultValue: false
	},
	"wrap": {
		type: "Enum",
		enumValues: [go.TextBlock.WrapDesiredSize, go.TextBlock.WrapFit, go.TextBlock.None],
		defaultValue: go.TextBlock.WrapDesiredSize
	},
	"overflow": {
		type: "Enum",
		enumValues: [go.TextBlock.OverflowClip, go.TextBlock.OverflowEllipsis],
		defaultValue: go.TextBlock.OverflowClip
	},
	"stroke": {
		type: "Brush",
		defaultValue: "black"
	},
	"lineCount": {
		type: "number",
		readOnly: true
	},
	"editable": { type: "boolean" }
});

(<any>go.Panel)["_inspectedProperties"] = ({
	"type": {
		type: "Enum",
		enumValues: [go.Panel.Position, go.Panel.Vertical, go.Panel.Horizontal, go.Panel.Auto, go.Panel.Spot, go.Panel.Table, go.Panel.Viewbox,
		go.Panel.Link, go.Panel.TableRow, go.Panel.TableColumn, go.Panel.Grid],
		defaultValue: go.Panel.Position
	},
	"padding": {
		type: "Margin",
		defaultValue: new go.Margin()
	},
	"defaultAlignment": {
		type: "Spot",
		defaultValue: go.Spot.Default
	},
	"defaultStretch": {
		type: "Enum",
		enumValues: [go.GraphObject.None, go.GraphObject.Fill, go.GraphObject.Horizontal, go.GraphObject.Vertical, go.GraphObject.Default],
		defaultValue: go.GraphObject.Default
	},
	"gridCellSize": {
		type: "Size",
		defaultValue: new go.Size(10, 10)
	},
	"gridOrigin": {
		type: "Point",
		defaultValue: new go.Point()
	}
});

(<any>go.Part)["_inspectedProperties"] = ({
	"location": {
		type: "Point"
	},
	"locationSpot": { type: "Spot" },
	"resizable": {
		type: "boolean",
		defaultValue: false
	},
	"rotatable": {
		type: "boolean",
		defaultValue: false
	},
	"isShadowed": {
		type: "boolean",
		defaultValue: false
	}
	/*  ,
	"locationObjectName": null,
	"selectionObjectName": null,
	"layerName": null,
	"category": null,
	"resizeObjectName": null,
	"rotateObjectName": null,
	"text": null,
	"shadowOffset": null,
	"shadowColor": null,
	"shadowBlur": null*/
});

(<any>go.Node)["_inspectedProperties"] = ({
	"isTreeExpanded": {
		type: "boolean",
		defaultValue: true
	}
	/*,
	"avoidable": null,
	"avoidableMargin": null*/
});

(<any>go.Link)["_inspectedProperties"] = ({
	"fromSpot": { type: "Spot" },
	"toSpot": { type: "Spot" },
	"relinkableFrom": {
		type: "boolean",
		defaultValue: false
	},
	"relinkableTo": {
		type: "boolean",
		defaultValue: false
	},
	"resegmentable": {
		type: "boolean",
		defaultValue: false
	},
	// technically a property of Part, only used on Links
	"reshapable": {
		type: "boolean",
		defaultValue: false
	},
	"midPoint": {
		type: "Point",
		readOnly: true
	},
	"midAngle": {
		type: "number",
		readOnly: true
	},
	"isOrthogonal": {
		type: "boolean",
		readOnly: true
	},
	"isAvoiding": {
		type: "boolean",
		readOnly: true
	},
	"corner": {
		type: "number",
		defaultValue: 0
	},
	"curve": {
		type: "Enum",
		enumValues: [go.Link.None, go.Link.Bezier, go.Link.JumpGap, go.Link.JumpOver],
		defaultValue: go.Link.None
	},
	"curviness": {
		type: "number",
		defaultValue: NaN
	},
	"routing": {
		type: "Enum",
		enumValues: [go.Link.Normal, go.Link.Orthogonal, go.Link.AvoidsNodes],
		defaultValue: go.Link.Normal
	},
	"smoothness": {
		type: "number",
		defaultValue: 0.5
	}
});

(<any>go.Group)["_inspectedProperties"] = ({
	"ungroupable": {
		type: "boolean",
		defaultValue: false
	},
	"isSubGraphExpanded": {
		type: "boolean",
		defaultValue: true
	}
});

(<any>go.Picture)["_inspectedProperties"] = ({
	"source": { type: "string" },
	"imageStretch": {
		type: "Enum",
		enumValues: [go.GraphObject.None, go.GraphObject.Fill, go.GraphObject.Uniform, go.GraphObject.UniformToFill],
		defaultValue: go.GraphObject.None
	},
	"naturalBounds": {
		type: "Rect",
		readOnly: true
	},
	"sourceRect": {
		type: "Rect",
		defaultValue: new go.Rect(NaN, NaN, NaN, NaN)
	}
});

(<any>go.Brush).nullable = true;