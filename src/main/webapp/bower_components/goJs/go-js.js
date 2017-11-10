/**
 * go Js
 */
(function () {
    'use strict';

    angular.module('minimal', [])
        .directive('goDiagram', function () {
            return {
                restrict: 'E',
                template: '<div></div>',  // just an empty DIV element
                replace: true,
                scope: { model: '=goModel' },
                link: function (scope, element, attrs) {

                    var $ = go.GraphObject.make;
                    var simpletemplate =
                        $(go.Node, "Auto",
                            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                            $(go.Shape, "SquareArrow",
                                new go.Binding("fill", "color"),
                                {
                                    portId: "", cursor: "pointer", strokeWidth: 0,
                                    fromLinkable: true, toLinkable: true,
                                    fromLinkableSelfNode: true, toLinkableSelfNode: true,
                                    fromLinkableDuplicates: true, toLinkableDuplicates: true
                                }),
                            $(go.Panel, "Table",
                                { defaultAlignment: go.Spot.Left },
                                $(go.TextBlock, { row: 0, column: 0, margin: 15, editable: false, stroke: "white" },
                                    new go.Binding("text", "name").makeTwoWay())
                            ),
                            $("Button",
                                { alignment: go.Spot.TopRight },
                                $(go.Shape, "AsteriskLine", { width: 8, height: 8 }),
                                { click: changeCategory })
                        );
                    var detailtemplate = $(go.Node, "Auto",
                        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                        $(go.Shape, "SquareArrow",
                            new go.Binding("fill", "color"),
                            {
                                portId: "", cursor: "pointer", strokeWidth: 0,
                                fromLinkable: true, toLinkable: true,
                                fromLinkableSelfNode: true, toLinkableSelfNode: true,
                                fromLinkableDuplicates: true, toLinkableDuplicates: true
                            }),
                        $(go.Panel, "Table",
                            { defaultAlignment: go.Spot.Left },
                            $(go.TextBlock, { row: 0, column: 0, margin: 8, editable: false, stroke: "white" },
                                new go.Binding("text", "name").makeTwoWay()),
                            $(go.TextBlock, { row: 1, column: 1, editable: false, stroke: "white", font: "bold" },
                                new go.Binding("text", "desc"))
                        ),
                        $("Button",
                            { alignment: go.Spot.TopRight },
                            $(go.Shape, "AsteriskLine", { width: 8, height: 8 }),
                            { click: changeCategory })
                    );

                    var templmap = new go.Map("string", go.Node);
                    templmap.add("simple", simpletemplate);
                    templmap.add("detailed", detailtemplate);

                    var diagram =  // create a Diagram for the given HTML DIV element
                        $(go.Diagram, element[0],
                            {
                                nodeTemplateMap: templmap,
                                linkTemplate: $(go.Link,
                                    { relinkableFrom: true, relinkableTo: true },
                                    $(go.Shape),
                                    $(go.Shape, { toArrow: "Standard", stroke: null, strokeWidth: 0 })
                                ),
                                initialContentAlignment: go.Spot.Center,
                                "ModelChanged": updateAngular,
                                "ChangedSelection": updateSelection,
                                "undoManager.isEnabled": true
                            });

                    // this function changes the category of the node data to cause the Node to be replaced
                    function changeCategory(e, obj) {
                        var node = obj.part;
                        if (node) {
                            var diagram = node.diagram;
                            diagram.startTransaction("changeCategory");
                            var cat = diagram.model.getCategoryForNodeData(node.data);
                            if (cat === "simple")
                                cat = "detailed";
                            else
                                cat = "simple";
                            diagram.model.setCategoryForNodeData(node.data, cat);
                            diagram.commitTransaction("changeCategory");
                        }
                    }
                    // whenever a GoJS transaction has finished modifying the model, update all Angular bindings
                    function updateAngular(e) {
                        if (e.isTransactionFinished) {
                            scope.$apply();
                        }
                    }
                    // update the Angular model when the Diagram.selection changes
                    function updateSelection(e) {
                        diagram.model.selectedNodeData = null;
                        var it = diagram.selection.iterator;
                        while (it.next()) {
                            var selnode = it.value;
                            // ignore a selected link or a deleted node
                            if (selnode instanceof go.Node && selnode.data !== null) {
                                diagram.model.selectedNodeData = selnode.data;
                                break;
                            }
                        }
                        scope.$apply();
                    }

                    // notice when the value of "model" changes: update the Diagram.model
                    scope.$watch("model", function (newmodel) {
                        var oldmodel = diagram.model;
                        if (oldmodel !== newmodel) {
                            diagram.removeDiagramListener("ChangedSelection", updateSelection);
                            diagram.model = newmodel;
                            diagram.addDiagramListener("ChangedSelection", updateSelection);
                        }
                    });
                    scope.$watch("model.selectedNodeData.name", function (newname) {
                        if (!diagram.model.selectedNodeData) return;
                        // disable recursive updates
                        diagram.removeModelChangedListener(updateAngular);
                        // change the name
                        diagram.startTransaction("change name");
                        // the data property has already been modified, so setDataProperty would have no effect
                        var node = diagram.findNodeForData(diagram.model.selectedNodeData);
                        if (node !== null) node.updateTargetBindings("name");
                        diagram.commitTransaction("change name");
                        // re-enable normal updates
                        diagram.addModelChangedListener(updateAngular);
                    });
                }
            };
        });
})();