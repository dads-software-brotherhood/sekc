(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompletitionDialogController',PracticeManagementCompletitionDialogController);

    PracticeManagementCompletitionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementCompletitionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
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
