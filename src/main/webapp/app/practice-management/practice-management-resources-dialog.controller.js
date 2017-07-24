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

        vm.load();
        

        function load() {

            vm.resourcesTypes = localStorageService.get('resourcesTypes');
            
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
