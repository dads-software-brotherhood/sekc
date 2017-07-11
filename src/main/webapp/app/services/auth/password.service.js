(function() {
    'use strict';

    angular
        .module('sekcApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/v1/account/change_password', {}, {});

        return service;
    }
})();
