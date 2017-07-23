(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementResourcesDialogController',PracticeManagementResourcesDialogController);

    PracticeManagementResourcesDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementResourcesDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
