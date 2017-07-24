(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementResourcesDialogController',PracticeManagementResourcesDialogController);

    PracticeManagementResourcesDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementResourcesDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clear = clear;
        vm.load = load;
        vm.cancel = cancel;
        vm.save = save;
        

        vm.load();
        

        function load() {

            vm.resourcesTypes = localStorageService.get('resourcesTypes');
            vm.practice = localStorageService.get('practiceInEdition');
        	
        	if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
                
            }
 
        }

        function save () { 
        	
        	if(vm.type != null && vm.description != null){
        		
        		vm.practice.thingsToDo.resources.push({idTypeResource : vm.type.id, content : vm.description});
                localStorageService.set('practiceInEdition', vm.practice);
                vm.clear();
                $uibModalInstance.dismiss('cancel');

            }
            
        }
        
        
        function clear () {
        	vm.description = null;
        	vm.type = null;
        }
        
        function cancel () {
        	
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
