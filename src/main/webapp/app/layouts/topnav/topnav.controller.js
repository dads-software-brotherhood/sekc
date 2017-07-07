(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('TopnavController', TopnavController);

    TopnavController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function TopnavController ($state, Auth, Principal, ProfileService, LoginService) {

    }
})();