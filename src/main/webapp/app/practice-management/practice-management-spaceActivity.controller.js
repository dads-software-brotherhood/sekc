(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter', '$location', 'Practice'];

    function PracticeManagementSpaceActivityController($stateParams, JhiLanguageService, localStorageService, $filter, $location, Practice) {
        var vm = this;

        vm.load = load;
        vm.practice = null;

        vm.addActivity = addActivity;
        vm.activityInEdition = null;
        vm.deleteActivity = deleteActivity;

        vm.competency = null;
        vm.competencyLevel = null;
        vm.addCompetency = addCompetency;
        vm.deleteCompetency = deleteCompetency;

        vm.nameApproach = null;
        vm.descriptionApproach = null;
        vm.addApproach = addApproach;
        vm.deleteApproach = deleteApproach;

        vm.alpha = null;
    	vm.alphaState = null;
    	vm.workProduct = null;
    	vm.levelOfDetail = null;
    	vm.descriptionEntry = null;
        vm.addEntry= addEntry;
        vm.deleteEntry = deleteEntry;
        
        vm.alphaCompletition = null;
    	vm.alphaStateCompletition = null;
    	vm.workProductCompletition = null;
    	vm.levelOfDetailCompletition = null;
    	vm.descriptionCompletition = null;
        vm.addCompletition= addCompletition;
        vm.deleteCompletition = deleteCompletition;
        
        vm.actionInEdition = newAction();
        vm.addAction = addAction;
        vm.deleteAction = deleteAction;
        vm.deleteActionElement = deleteActionElement;
        vm.newAction = newAction;
        vm.fillAction = fillAction;
        
        vm.type = null;
		vm.descriptionCompletition = null;
		vm.attachment = null;
		vm.addResource= addResource;
	    vm.deleteResource = deleteResource;
       
        vm.save = save;
        vm.clean = clean;
        		
        vm.load();
        

        function load() {
        	vm.practice = localStorageService.get('practiceInEdition');
        	vm.activitySpaces = localStorageService.get('activitySpaces');
            vm.competencies = localStorageService.get('competencies');

            vm.actionsKinds = localStorageService.get('actionsKinds');
            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
            vm.resourcesTypes = localStorageService.get('resourcesTypes');

            vm.activityInEdition = newActivity();
            if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	vm.practice.thingsToDo = { activities: [] }
            }
	       
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }

        //---------------- Activity -----------------

        function addActivity()
        {
            vm.practice.thingsToDo.activities.push(vm.activityInEdition);
            vm.activityInEdition = newActivity();
        }

        function newActivity()
        {
            return {
                competencies: [],
                approaches: [],
                actions: [],
                resources: [],
                entryCriterion: {
                    alphaStates: [],
                    workProductsLevelofDetail: [],
                    otherConditions: []
                },
                completitionCriterion: {
                    alphaStates: [],
                    workProductsLevelofDetail: [],
                    otherConditions: []
                }
            };
        }
        
        function deleteActivity (index){
        	vm.practice.thingsToDo.activities.splice(index, 1);
        }

        //---------------- Competency -----------------

        function addCompetency() {
            console.log(vm.activityInEdition);
            if (vm.competency != null && vm.competency != undefined &&
                vm.competencyLevel != null && vm.competencyLevel != undefined)
            {
                vm.activityInEdition.competencies.push({
                    idCompetency: vm.competency.id,
                    competency: vm.competency.name,
                    idCompetencyLevel: vm.competencyLevel.id,
                    competencyLevel: vm.competencyLevel.level + ' / ' + vm.competencyLevel.name
                });
                vm.competency = null;
                vm.competencyLevel = null;
                $('#competencyDialog').modal('toggle');
            }
            console.log(vm.activityInEdition);
        }
        function deleteCompetency (index) {
            vm.activityInEdition.competencies.splice(index, 1);
        }

        //---------------- Approach -----------------
        
        function addApproach()
        {
            if (vm.nameApproach != null && vm.descriptionApproach != null) {

                vm.activityInEdition.approaches.push({
                    name: vm.nameApproach,
                    description: vm.descriptionApproach
                });
                vm.nameApproach = null;
                vm.descriptionApproach = null;
                $('#approachDialog').modal('toggle');
            }
        }

        function deleteApproach(index) {
            vm.activityInEdition.approaches.splice(index, 1);
        }

        //---------------- Action -----------------
        
        function newAction() 
        {
            return {
                idActionKind: null,
                alphaStates: [],
                workProductsLevelofDetail: []
        		}
        }
        
        function fillAction() {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined)
            {
                vm.actionInEdition.alphaStates.push(
                    {
                        description: vm.alpha.name + ' / ' + vm.alphaState.name,
                        idAlpha: vm.alpha.id, idState: vm.alphaState.id
                    }
                );
                vm.alpha = null;
                vm.alphaState = null;
                vm.actionKind = null;
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.actionInEdition.workProductsLevelofDetail.push({
                    description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name,
                    idWorkProduct: vm.workProduct.id,
                    idLevelOfDetail: vm.levelOfDetail.id,
                });
                vm.workProduct = null;
                vm.levelOfDetail = null;
                vm.actionKind = null;
            }   
        }

        function deleteActionElement(index, tipo)
        {
            if (tipo === "alpha")
            {
                vm.actionInEdition.alphaStates.splice(index, 1);
            }
            else if (tipo === "workproduct")
            {
                vm.actionInEdition.workProductsLevelofDetail.splice(index, 1);
            }
        }

        function addAction()
        {
            if (vm.actionInEdition.idActionKind != null &&
                (vm.actionInEdition.alphaStates.length > 0 || vm.actionInEdition.workProductsLevelofDetail.length > 0)
            )
            {
                vm.activityInEdition.actions.push(vm.actionInEdition);
                vm.actionInEdition = newAction();
                $('#actionDialog').modal('toggle');
            }
        }

        function deleteAction(index) {
            vm.activityInEdition.actions.splice(index, 1);
        }

        //---------------- Entry -----------------
        
        function addEntry() {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {
            	vm.activityInEdition.entryCriterion.alphaStates.push({
                    description: vm.alpha.name + ' / ' + vm.alphaState.name,
                    idAlpha: vm.alpha.id,
                    idState: vm.alphaState.id,
                    briefDescription: vm.descriptionEntry
                });
               vm.alpha = null;
                vm.alphaState = null;
            	vm.descriptionEntry = null;
                $('#entryDialog').modal('toggle');
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.activityInEdition.entryCriterion.workProductsLevelofDetail.push({
                    description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name,
                    idWorkProduct: vm.workProduct.id,
                    idLevelOfDetail: vm.levelOfDetail.id,
                    briefDescription: vm.descriptionEntry
                });
                vm.workProduct = null;
                vm.levelOfDetail = null;
            	vm.descriptionEntry = null;
                $('#entryDialog').modal('toggle');
            } else if (vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
                vm.anotherEntryCriteria != "") {
                vm.activityInEdition.entryCriterion.otherConditions.push({
                    description: vm.anotherEntryCriteria,
                    briefDescription: vm.descriptionEntry
                });
                vm.anotherEntryCriteria = null;
            	vm.descriptionEntry = null;
                $('#entryDialog').modal('toggle');
            }
        }

        function deleteEntry (index, tipo) {
        	if(tipo === 'alpha'){
        		vm.activityInEdition.entryCriterion.alphaStates.splice(index, 1);
        	}else if(tipo === 'workproduct'){
        		vm.activityInEdition.entryCriterion.workProductsLevelofDetail.splice(index, 1);
        	}else if(tipo === 'other'){
        		vm.activityInEdition.entryCriterion.otherConditions.splice(index, 1);
        	} 	
        }
        
        //---------------- Completition -----------------
        
        function addCompletition() {
            if (vm.alphaCompletition != null && vm.alphaCompletition != undefined &&
                vm.alphaStateCompletition != null && vm.alphaStateCompletition != undefined) {
            	vm.activityInEdition.completitionCriterion.alphaStates.push({
                    description: vm.alphaCompletition.name + ' / ' + vm.alphaStateCompletition.name,
                    idAlpha: vm.alphaCompletition.id,
                    idState: vm.alphaStateCompletition.id,
                    briefDescription: vm.descriptionCompletition
                });
               vm.alphaCompletition = null;
                vm.alphaStateCompletition = null;
            	vm.descriptionCompletition = null;
                $('#completitionDialog').modal('toggle');
            } else if (vm.workProductCompletition != null && vm.workProductCompletition != undefined &&
                vm.levelOfDetailCompletition != null && vm.levelOfDetailCompletition != undefined) {

                vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.push({
                    description: vm.workProductCompletition.name + ' / ' + vm.levelOfDetailCompletition.name,
                    idWorkProduct: vm.workProductCompletition.id,
                    idLevelOfDetail: vm.levelOfDetailCompletition.id,
                    briefDescription: vm.descriptionCompletition
                });
                vm.workProductCompletition = null;
                vm.levelOfDetailCompletition = null;
            	vm.descriptionCompletition = null;
                $('#completitionDialog').modal('toggle');
            } else if (vm.anotherEntryCriteriaCompletition != null && vm.anotherEntryCriteriaCompletition != undefined &&
                vm.anotherEntryCriteriaCompletition != "") {
                vm.activityInEdition.completitionCriterion.otherConditions.push({
                    description: vm.anotherEntryCriteriaCompletition,
                    briefDescription: vm.descriptionCompletition
                });
                vm.anotherEntryCriteriaCompletition = null;
            	vm.descriptionCompletition = null;
                $('#completitionDialog').modal('toggle');
            }
        }
        
        function deleteCompletition (index, tipo) {
        	if(tipo === 'alpha'){
        		vm.activityInEdition.completitionCriterion.alphaStates.splice(index, 1);
        	}else if(tipo === 'workproduct'){
        		vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.splice(index, 1);
        	}else if(tipo === 'other'){
        		vm.activityInEdition.completitionCriterion.otherConditions.splice(index, 1);
        	} 	
        }
        
        //---------------- Resource -----------------
        
        function addResource() {
	        if(vm.type != null && vm.descriptionResource != null && vm.attachment != null){
	    		var file = new FormData();
	    		file.append('file', vm.attachment);
	            
	    		vm.activityInEdition.resources.push({idTypeResource : vm.type.id, content : vm.descriptionResource, file : vm.attachment, fileName : vm.attachment.name});
	    		vm.type = null;
	    		vm.descriptionResource = null;
	    		vm.attachment = null;
	            $('#resourceDialog').modal('toggle');
	        }
        }

        function deleteResource (index) {
        	vm.activityInEdition.resources.splice(index, 1);
        }
           
        function save() {
        	console.log(vm.practice);
            localStorageService.set('practiceInEdition', vm.practice);
            if (vm.practice.id !== null && vm.practice.id !== undefined) {
                Practice.update(vm.practice, onSaveSuccess, onSaveError);
            } else {
            	Practice.save(vm.practice, onSaveSuccess, onSaveError);
            }
        }
        
        function onSaveSuccess (result) {
//          vm.practice = {};
//          localStorageService.set('practiceInEdition', null);
            $location.path('/practice-management');
        }

        function onSaveError () {
        }
    	
        function clean() {
        	vm.activityInEdition = null;
    	}
	}
        
    
})();
