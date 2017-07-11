(function() {
    'use strict';

    angular
        .module('sekcApp')
        .factory('PasswordResetInit', PasswordResetInit);

    PasswordResetInit.$inject = ['$resource'];

    function PasswordResetInit($resource) {
        var service = $resource('api/v1/account/reset_password/init', {}, {});

        return service;
    }
})();
