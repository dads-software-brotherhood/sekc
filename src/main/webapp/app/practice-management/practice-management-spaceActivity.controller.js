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
        vm.cleanModales = cleanModales;
        vm.cleanModales();

        vm.addCompetency = addCompetency;
        vm.deleteCompetency = deleteCompetency;
        
        vm.addApproach = addApproach;
        vm.deleteApproach = deleteApproach;
        
        vm.addEntry= addEntry;
        vm.deleteEntry = deleteEntry;
        vm.alphaEntryFilter = alphaEntryFilter;
        vm.workProductsEntryFilter = workProductsEntryFilter 
        
        vm.addCompletition= addCompletition;
        vm.deleteCompletition = deleteCompletition;
        vm.alphaCompletitionFilter = alphaCompletitionFilter;
        vm.workProductsCompletitionFilter = workProductsCompletitionFilter;
        
        vm.actionInEdition = newAction();
        vm.addAction = addAction;
        vm.deleteAction = deleteAction;
        vm.deleteActionElement = deleteActionElement;
        vm.newAction = newAction;
        vm.fillAction = fillAction;
        vm.alphaStateFilter = alphaStateFilter;
        vm.workProductsFilter = workProductsFilter;
        vm.competenciesFilter = competenciesFilter;
        
        
		vm.addResource= addResource;
	    vm.deleteResource = deleteResource;
       
        vm.save = save;
        vm.clean = clean;
        		
        vm.load();
        

        function load()
        {
            vm.practice = localStorageService.get('practiceInEdition');
            if (vm.practice == null) {
                vm.practice = {};
            }
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
            vm.activityInEdition.idActivitySpace = vm.idActivitySpace.id;
            vm.activityInEdition.nameActivitySpace = vm.idActivitySpace.name;
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

        function addCompetency()
        {
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
                vm.cleanModales();
                $('#competencyDialog').modal('toggle');
            }
            console.log(vm.activityInEdition);
        }
        function deleteCompetency(index)
        {
            vm.activityInEdition.competencies.splice(index, 1);
        }
        function competenciesFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.competencies, {
                idCompetency: vm.competency.id,
                idCompetencyLevel: alphaState.id
            }).length == 0;
        }

        //---------------- Approach -----------------
        
        function addApproach()
        {
            if (vm.nameApproach != null && vm.descriptionApproach != null) {

                vm.activityInEdition.approaches.push({
                    name: vm.nameApproach,
                    description: vm.descriptionApproach
                });
                vm.cleanModales();
                $('#approachDialog').modal('toggle');
            }
        }
        function deleteApproach(index)
        {
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
        function deleteEntry(index, tipo)
        {
        	if(tipo === 'alpha'){
        		vm.activityInEdition.entryCriterion.alphaStates.splice(index, 1);
        	}else if(tipo === 'workproduct'){
        		vm.activityInEdition.entryCriterion.workProductsLevelofDetail.splice(index, 1);
        	}else if(tipo === 'other'){
        		vm.activityInEdition.entryCriterion.otherConditions.splice(index, 1);
        	} 	
        }
        function alphaEntryFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.entryCriterion.alphaStates, {
                idAlpha: vm.alpha.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsEntryFilter(levelOfDetails) {
            return $filter('filter')(vm.activityInEdition.entryCriterion.workProductsLevelofDetail, {
                idWorkProduct: vm.workProduct.id,
                idLevelOfDetail: levelOfDetails.id,
            }).length == 0;
        }
        //---------------- Completition -----------------
        
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
        function deleteCompletition(index, tipo)
        {
        	if(tipo === 'alpha'){
        		vm.activityInEdition.completitionCriterion.alphaStates.splice(index, 1);
        	}else if(tipo === 'workproduct'){
        		vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.splice(index, 1);
        	}else if(tipo === 'other'){
        		vm.activityInEdition.completitionCriterion.otherConditions.splice(index, 1);
        	} 	
        }
        function alphaCompletitionFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.completitionCriterion.alphaStates, {
                idAlpha: vm.alphaCompletition.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsCompletitionFilter(levelOfDetails) {
            return $filter('filter')(vm.activityInEdition.completitionCriterion.workProductsLevelofDetail, {
                idWorkProduct: vm.workProductCompletition.id,
                idLevelOfDetail: levelOfDetails.id,
            }).length == 0;
        }
        
        //---------------- Resource -----------------
        
        function addResource()
        {
            if (vm.type != null && vm.descriptionResource != null && vm.attachment != null) {
                vm.activityInEdition.resources.push({ idTypeResource: vm.type.id, content: vm.descriptionResource, file: vm.attachment.base64, fileName: vm.attachment.filename });
                vm.cleanModales();
	            $('#resourceDialog').modal('toggle');
	        }
        }
        function deleteResource(index)
        {
        	vm.activityInEdition.resources.splice(index, 1);
        }
           
        function save()
        {
            console.log(vm.practice);
            console.log(JSON.stringify(vm.practice, null, "\t"));
            localStorageService.set('practiceInEdition', vm.practice);
            if (vm.practice.id !== null && vm.practice.id !== undefined) {
                Practice.update(vm.practice, onSaveSuccess, onSaveError);
            } else {
            	Practice.save(vm.practice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result)
        {
            vm.practice = {};
            localStorageService.set('practiceInEdition', null);
            $location.path('/practice-management');
        }

        function onSaveError()
        {
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
