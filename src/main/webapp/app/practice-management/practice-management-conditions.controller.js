(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementConditionsController',PracticeManagementConditionsController);

    PracticeManagementConditionsController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter', '$location'];

    function PracticeManagementConditionsController ($stateParams, JhiLanguageService, localStorageService, $filter, $location) {
        var vm = this;

        vm.load = load;
        vm.practice = null;
        vm.indexMeasure = -1;
        vm.measures = [];
        
        //measure
        vm.deleteMeasure = deleteMeasure;
        vm.cleanMeasure = cleanMeasure;
        vm.addMeasure = addMeasure;
        
        //entries y results
        vm.addEntry = addEntry;
        vm.addResult = addResult;
        vm.deleteEntry = deleteEntry;
        vm.deleteResult = deleteResult;
        
        vm.save = save;
        
		
        vm.load();
        
        vm.clean = clean;
        vm.cleanEntries = cleanEntries;
        vm.cleanResults = cleanResults;
        

        function load() {
        	vm.practice = localStorageService.get('practiceInEdition');
        	vm.alphas = localStorageService.get('alphas');
            vm.workProducts = localStorageService.get('workproducts');
            
	        console.log(vm.practice);
	        
	        if (angular.isUndefined(vm.practice.conditions) || vm.practice.conditions === null) {
	        	
	        	vm.practice.conditions = { entries : [], results : [], measures : [] }
                
            }
	        if (!(angular.isUndefined(vm.practice.conditions.measures) || vm.practice.conditions.measures === null))
            {
                vm.measures = vm.practice.conditions.measures;
            }
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function addMeasure() {
    		
            if (!vm.newMeasure)
                return;
            
            if(vm.indexMeasure == -1)
            {
            	vm.measures.push({name: vm.newMeasure});
            }else{
            	vm.measures[vm.indexMeasure].measure = vm.newMeasure;
            }
            console.log(vm.measures);
            vm.cleanMeasure();
    	}
        
        function deleteMeasure (index) {
            vm.measures.splice(index, 1);
        }
    	
        
    	function cleanMeasure () {
             vm.newMeasure = '';
             vm.indexMeasure = -1;
        }
        
        function addEntry() {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {

                vm.practice.conditions.entries.push({ description: vm.alpha.name + ' / ' + vm.alphaState.name, idAlpha: vm.alpha.id, idState: vm.alphaState.id });
            
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.practice.conditions.entries.push({ description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name, idWorkProduct: vm.workProduct.id, idLevelOfDetail: vm.levelOfDetail.id });

            } else if (vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
                vm.anotherEntryCriteria != "") {

                vm.practice.conditions.entries.push({ description: vm.anotherEntryCriteria });
            }
            vm.cleanEntries();

        }

        function addResult() {
        	if(vm.alphaResult != null && vm.alphaResult != undefined &&
                vm.alphaStateResult != null && vm.alphaStateResult != undefined){
        		
                vm.practice.conditions.results.push({ description: vm.alphaResult.name + ' / ' + vm.alphaStateResult.name, idAlpha: vm.alphaResult.id, idState: vm.alphaStateResult.id });

            } else if (vm.workProductResult != null && vm.workProductResult != undefined &&
        			vm.levelOfDetailResult != null && vm.levelOfDetailResult != undefined){

                vm.practice.conditions.results.push({ description: vm.workProductResult.name + ' / ' + vm.levelOfDetailResult.name, idWorkProduct: vm.workProductResult.id, idLevelOfDetail: vm.levelOfDetailResult.id });
            		
        	}else if(vm.anotherEntryCriteriaResult != null && vm.anotherEntryCriteriaResult != undefined){

                vm.practice.conditions.results.push({description: vm.anotherEntryCriteriaResult});
            }
            vm.cleanResults();
    	
        }
    	
    	function deleteEntry (index) {
    		vm.practice.conditions.entries.splice(index, 1);
    	}
    	
    	function deleteResult (index) {
    		vm.practice.conditions.results.splice(index, 1);
    	}

        function cleanEntries() {
            vm.alpha = null;
            vm.alphaState = null;
            vm.workProduct = null;
            vm.levelOfDetail = null;
            vm.anotherEntryCriteria = null;
        }

        function cleanResults() {
            vm.alphaResult = null;
            vm.alphaStateResult = null;
            vm.workProductResult = null;
            vm.levelOfDetailResult = null;
            vm.anotherEntryCriteriaResult = null;
        }
           
        function save() {
        	vm.practice.conditions.measures = vm.measures;
        	console.log(vm.practice)
            localStorageService.set('practiceInEdition', vm.practice);
        	console.log($location.path());
	        $location.path('/practice-management/practiceThingswork/');
        }
    	
        function clean() {
        	vm.practice.conditions = null;
        	localStorageService.set('practiceInEdition', vm.practice);
        	load();
    	}
	}
        
    
})();
