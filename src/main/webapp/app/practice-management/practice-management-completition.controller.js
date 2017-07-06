(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompletionController', PracticeManagementCompletionController);

    PracticeManagementCompletionController.$inject = ['State', 'AlertService'];

    function PracticeManagementCompletionController (State, AlertService) {
        var vm = this;

        vm.load = load;
        vm.states = {};

        vm.load();
        
        function load () {
        	State.query(onSuccess, onError);
        }
        
        function onSuccess(data, headers) {
            vm.states = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }
        
     
        
    }
        
})();
