(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementEntryDialogController',PracticeManagementEntryDialogController);

    PracticeManagementEntryDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementEntryDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
