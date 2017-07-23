(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementActionDialogController',PracticeManagementActionDialogController);

    PracticeManagementActionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementActionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;
        
        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
