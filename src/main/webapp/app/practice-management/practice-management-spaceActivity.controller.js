(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter'];

    function PracticeManagementSpaceActivityController ($stateParams, JhiLanguageService, localStorageService, $filter) {
        var vm = this;

        vm.load = load;
        vm.practice = null;
        
        
        vm.deleteApproach = deleteApproach;
        
        vm.save = save;
        vm.cancel = cancel;
        
		
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
           
        function save() {
        	console.log(vm.practice)
            localStorageService.set('practiceInEdition', vm.practice);
        }
    	
        function cancel() {
            vm.practice = {};
            localStorageService.set('practiceInEdition', null);
    	}
        
        function deleteApproach (index) {
    		vm.practice.thingsToDo.approaches.splice(index, 1);
    		localStorageService.set('practiceInEdition', vm.practice);
        	
        }
	}
        
    
})();
