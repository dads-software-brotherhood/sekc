(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope','$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($scope,$state, Auth, Principal, ProfileService, LoginService) {
     var vm = this;

        vm.isTopnavController = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
            getAccount();
        }

        function toggleNavbar() {
            vm.isTopnavController = !vm.isTopnavController;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }  
        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
            });
        }
    }
})();
