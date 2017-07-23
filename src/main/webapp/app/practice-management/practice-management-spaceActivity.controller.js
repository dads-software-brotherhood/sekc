(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService'];

    function PracticeManagementSpaceActivityController ($stateParams,  $uibModalInstance, JhiLanguageService) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        
        function save () {
            
        }
        
       
        
    }
})();
