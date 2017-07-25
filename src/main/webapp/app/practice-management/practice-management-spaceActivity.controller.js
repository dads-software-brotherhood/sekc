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
                
            }
	       
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function deleteApproach (index) {
    		vm.practice.thingsToDo.approaches.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);
        }
        
        function deleteAction (index) {
    		vm.practice.thingsToDo.actions.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);     	
        }
        
        function deleteEntry (index) {
    		vm.practice.thingsToDo.entryCriterion.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);   	
        }
        
        function deleteCompletition (index) {
    		vm.practice.thingsToDo.completitionCriterion.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);   	
        }
        
        function deleteResource (index) {
    		vm.practice.thingsToDo.resources.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);   	
        }
        
        function deleteCompetency (index) {
        	vm.practice.thingsToDo.competencies.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice); 
        }
        
        function deleteActivitySpace(index) {
        	
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
//            vm.practice = {};
//          localStorageService.set('practiceInEdition', null);
            $location.path('/practice-management');
        }

        function onSaveError () {
        }
    	
        function clean() {
        	vm.practice.thingsToDo = null;
        	localStorageService.set('practiceInEdition', vm.practice);
        	load();
    	}
	}
        
    
})();
