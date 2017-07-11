(function() {
    'use strict';

    angular
        .module('sekcApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('api/v1/account/reset_password/finish', {}, {});

        return service;
    }
})();
