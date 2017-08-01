(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter', '$location', 'Practice'];

    function PracticeManagementSpaceActivityController ($stateParams, JhiLanguageService, localStorageService, $filter, $location, Practice) {
        var vm = this;

        vm.load = load;
        vm.practice = null;
        
        vm.deleteApproach = deleteApproach;
        vm.deleteAction = deleteAction;
        vm.deleteEntry = deleteEntry;
        vm.deleteCompletition = deleteCompletition;
        vm.deleteResource = deleteResource;
        vm.deleteActivitySpace = deleteActivitySpace;
        vm.deleteCompetency = deleteCompetency;
        
        vm.save = save;
        vm.clean = clean;
        		
        vm.load();
        

        function load() {
        	
        	vm.practice = localStorageService.get('practiceInEdition');
        	vm.activitySpaces = localStorageService.get('activitySpaces');
            vm.competencies = localStorageService.get('competencies');
            
	        console.log(vm.practice);
	        
	        
	        if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
		        vm.practice.thingsToDo.actions = { alphaStates : [], workProductsLevelofDetail : [] };
		        vm.practice.thingsToDo.entryCriterion = { alphaStates : [], workProductsLevelofDetail : [], otherConditions: [] }
		        vm.practice.thingsToDo.completitionCriterion = { alphaStates : [], workProductsLevelofDetail : [], otherConditions: [] }

            }
	       
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function deleteApproach (index) {
    		vm.practice.thingsToDo.approaches.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);
        }
        
        function deleteAction (index, tipo) {
        	if(tipo == 'alpha'){
        		vm.practice.thingsToDo.actions.alphaStates.splice(index, 1);
    		}else if(tipo == 'workproduct'){
        		vm.practice.thingsToDo.actions.workProductsLevelofDetail.splice(index, 1);
    		}
    		localStorageService.set('practiceInEdition', vm.practice);     	
        }
        
        function deleteEntry (index, tipo) {
        	if(tipo == 'alpha'){
        		vm.practice.thingsToDo.entryCriterion.alphaStates.splice(index, 1);
    		}else if(tipo == 'workproduct'){
        		vm.practice.thingsToDo.entryCriterion.workProductsLevelofDetail.splice(index, 1);
    		}else if(tipo == 'other'){
        		vm.practice.thingsToDo.entryCriterion.otherConditions.splice(index, 1);
    		}
    		localStorageService.set('practiceInEdition', vm.practice);   	
        }
        
        function deleteCompletition (index, tipo) {
        	if(tipo == 'alpha'){
        		vm.practice.thingsToDo.completitionCriterion.alphaStates.splice(index, 1);
    		}else if(tipo == 'workproduct'){
        		vm.practice.thingsToDo.completitionCriterion.workProductsLevelofDetail.splice(index, 1);
    		}else if(tipo == 'other'){
        		vm.practice.thingsToDo.completitionCriterion.otherConditions.splice(index, 1);
    		}
    		localStorageService.set('practiceInEdition', vm.practice);   	
        }
        function fillAction()
        {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined)
            {
                if (vm.actionInEdition.workProductsLevelofDetail.length) {
                    return;
                } else {
                    vm.actionInEdition.alphaStates.push(
                        {
                            description: vm.alpha.name + ' / ' + vm.alphaState.name,
                            idAlpha: vm.alpha.id, idState: vm.alphaState.id
                        }
                    );
                }
                vm.cleanModales();
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {
                if (vm.actionInEdition.alphaStates.length) {
                    return;
                } else {
                    vm.actionInEdition.workProductsLevelofDetail.push({
                        description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name,
                        idWorkProduct: vm.workProduct.id,
                        idLevelOfDetail: vm.levelOfDetail.id,
                    });
                }
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
                vm.cleanModales();
                $('#actionDialog').modal('toggle');
            }
        }
        function deleteAction(index) {
            vm.activityInEdition.actions.splice(index, 1);
        }
        function alphaStateFilter(alphaState) {
            return $filter('filter')(vm.actionInEdition.alphaStates, {
                idAlpha: vm.alpha.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsFilter(levelOfDetails) {
            return $filter('filter')(vm.actionInEdition.workProductsLevelofDetail, {
                idWorkProduct: vm.workProduct.id,
                idLevelOfDetail: levelOfDetails.id,
            }).length == 0;
        }

        //---------------- Entry -----------------
        
        function addEntry()
        {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {
            	vm.activityInEdition.entryCriterion.alphaStates.push({
                    description: vm.alpha.name + ' / ' + vm.alphaState.name,
                    idAlpha: vm.alpha.id,
                    idState: vm.alphaState.id
                });
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.activityInEdition.entryCriterion.workProductsLevelofDetail.push({
                    description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name,
                    idWorkProduct: vm.workProduct.id,
                    idLevelOfDetail: vm.levelOfDetail.id
                });
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            } else if (vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
                vm.anotherEntryCriteria != "") {
                vm.activityInEdition.entryCriterion.otherConditions.push(
                    vm.anotherEntryCriteria
                );
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            }
        }
        
        function addCompletition()
        {
            if (vm.alphaCompletition != null && vm.alphaCompletition != undefined &&
                vm.alphaStateCompletition != null && vm.alphaStateCompletition != undefined) {
            	vm.activityInEdition.completitionCriterion.alphaStates.push({
                    description: vm.alphaCompletition.name + ' / ' + vm.alphaStateCompletition.name,
                    idAlpha: vm.alphaCompletition.id,
                    idState: vm.alphaStateCompletition.id
                });
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            } else if (vm.workProductCompletition != null && vm.workProductCompletition != undefined &&
                vm.levelOfDetailCompletition != null && vm.levelOfDetailCompletition != undefined) {

                vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.push({
                    description: vm.workProductCompletition.name + ' / ' + vm.levelOfDetailCompletition.name,
                    idWorkProduct: vm.workProductCompletition.id,
                    idLevelOfDetail: vm.levelOfDetailCompletition.id
                });
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            } else if (vm.anotherEntryCriteriaCompletition != null && vm.anotherEntryCriteriaCompletition != undefined &&
                vm.anotherEntryCriteriaCompletition != "") {
                vm.activityInEdition.completitionCriterion.otherConditions.push(
                    vm.anotherEntryCriteriaCompletition
                );
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            }
        }
        
        //---------------- Resource -----------------
        
        function addResource()
        {
            if (vm.type != null && vm.descriptionResource != null && vm.attachment != null) {
                vm.activityInEdition.resources.push({ idTypeResource: vm.type.id, content: vm.descriptionResource, file: vm.attachment.base64, fileName: vm.attachment.filename});
                vm.cleanModales();
	            $('#resourceDialog').modal('toggle');
	        }
        }
           
        function save() {
        	console.log(vm.practice);
            localStorageService.set('practiceInEdition', vm.practice);
            
//            if (vm.practice.id !== null && vm.practice.id !== undefined) {
//                Practice.update(vm.practice, onSaveSuccess, onSaveError);
//            } else {
//            	Practice.save(vm.practice, onSaveSuccess, onSaveError);
//            }
        }
        
        function onSaveSuccess (result) {
//          vm.practice = {};
//          localStorageService.set('practiceInEdition', null);
            $location.path('/practice-management');
        }

        function onSaveError () {
        }

        function cleanModales()
        {
            vm.nameApproach = null;
            vm.descriptionApproach = null;

            vm.alphaCompletition = null;
            vm.alphaStateCompletition = null;
            vm.workProductCompletition = null;
            vm.levelOfDetailCompletition = null;
            vm.descriptionCompletition = null;

            vm.type = null;
            vm.attachment = null;
            angular.element("input[type='file']").val(null);
            vm.descriptionResource = null;

            vm.competency = null;
            vm.competencyLevel = null;

            vm.alpha = null;
            vm.alphaState = null;
            vm.workProduct = null;
            vm.levelOfDetail = null;
            vm.descriptionEntry = null;
            vm.anotherEntryCriteria = null;
            vm.anotherEntryCriteriaCompletition = null;
            
            
        }

        function clean()
        {
        	vm.activityInEdition = null;
        }
	}
        
    
})();
