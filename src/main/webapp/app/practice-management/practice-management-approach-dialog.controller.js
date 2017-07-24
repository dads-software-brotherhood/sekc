(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementApproachDialogController',PracticeManagementApproachDialogController);

    PracticeManagementApproachDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementApproachDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clear = clear;
        vm.load = load;
        vm.cancel = cancel;
        vm.save = save;
        vm.approaches = [];
        
        vm.load();
        

        function load() {
        	
        	vm.practice = localStorageService.get('practiceInEdition');
        	
        	if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
                
            }
 
        }

        function save () { 
        	
        	if(vm.name != null && vm.description != null){
        		
        		vm.practice.thingsToDo.approaches.push({name : vm.name, description : vm.description});
                localStorageService.set('practiceInEdition', vm.practice);
                vm.clear();
                $uibModalInstance.dismiss('cancel');

            }
            
        }
        
        
        function clear () {
        	vm.name = null;
        	vm.description = null;
        }
        
        function cancel () {
        	
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
