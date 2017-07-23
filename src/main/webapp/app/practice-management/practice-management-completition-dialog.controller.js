(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompletitionDialogController',PracticeManagementCompletitionDialogController);

    PracticeManagementCompletitionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementCompletitionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
