(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementDetailController', PracticeManagementDetailController);

    PracticeManagementDetailController.$inject = ['$stateParams', 'Practice'];

    function PracticeManagementDetailController ($stateParams, Practice) {
        var vm = this;

        vm.load = load;
        vm.practice = {};

        vm.load($stateParams.login);

        function load (login) {
            Practice.get({login: login}, function(result) {
                vm.practice = result;
            });
        }
    }
})();
