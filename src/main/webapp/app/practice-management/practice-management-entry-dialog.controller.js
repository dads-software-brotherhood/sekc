(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementEntryDialogController',PracticeManagementEntryDialogController);

    PracticeManagementEntryDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementEntryDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;
        vm.load = load;

        vm.load();
        

        function load() {

            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
              
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
