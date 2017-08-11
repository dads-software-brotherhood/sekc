(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('TopnavController', TopnavController);

    TopnavController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function TopnavController ($scope, $state, Auth, Principal, ProfileService, LoginService) {
    	var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        

        vm.login = login;
        vm.logout = logout;
        vm.$state = $state;

        function login() {
            LoginService.open();
        }

        function logout() {
            Auth.logout();
            $state.go('home');
            getAccount();
        }
        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
            });
        }
    }
})();