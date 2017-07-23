(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementApproachDialogController',PracticeManagementApproachDialogController);

    PracticeManagementApproachDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementApproachDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
