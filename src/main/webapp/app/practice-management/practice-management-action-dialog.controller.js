(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementActionDialogController',PracticeManagementActionDialogController);

    PracticeManagementActionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementActionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clear = clear;
        vm.load = load;

        vm.load();
        

        function load() {

            vm.actionsKinds = localStorageService.get('actionsKinds');
            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
            
        }
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
