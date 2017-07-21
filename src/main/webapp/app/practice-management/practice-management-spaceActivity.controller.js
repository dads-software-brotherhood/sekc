(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', 'JhiLanguageService'];

    function PracticeManagementSpaceActivityController ($stateParams, JhiLanguageService) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;

        function clear () {
        	
        }
        
        function save () {
            
        }
        
       
        
    }
})();
