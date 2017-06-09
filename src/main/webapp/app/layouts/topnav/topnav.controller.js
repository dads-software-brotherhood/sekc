(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('TopnavController', TopnavController);

    TopnavController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function TopnavController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isTopnavController = true;
        vm.isAuthenticated = Principal.isAuthenticated;

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
        }

        function toggleNavbar() {
            vm.isTopnavController = !vm.isTopnavController;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();