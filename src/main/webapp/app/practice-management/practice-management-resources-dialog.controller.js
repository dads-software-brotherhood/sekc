(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementResourcesDialogController',PracticeManagementResourcesDialogController);

    PracticeManagementResourcesDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementResourcesDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clean = clean;
        vm.load = load;
        vm.cancel = cancel;
        vm.save = save;
        vm.attachment = null;

        vm.load();
        

        function load() {

            vm.resourcesTypes = localStorageService.get('resourcesTypes');
            vm.practice = localStorageService.get('practiceInEdition');
        	
        	if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
                
            }
 
        }

        function save () { 
        	
        	if(vm.type != null && vm.description != null && vm.attachment != null){
        		
        		var file = new FormData();
        		file.append('file', vm.attachment);
                
        		vm.practice.thingsToDo.resources.push({idTypeResource : vm.type.id, content : vm.description, file : vm.attachment, fileName : vm.attachment.name});
                localStorageService.set('practiceInEdition', vm.practice);
                vm.clean();
                $uibModalInstance.dismiss('cancel');

            }
            
        }
        
        
        function clean () {
        	vm.description = null;
        	vm.type = null;
        }
        
        function cancel () {
        	
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
