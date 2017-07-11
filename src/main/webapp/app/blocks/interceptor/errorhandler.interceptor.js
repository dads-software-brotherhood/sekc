(function() {
    'use strict';

    angular
        .module('sekcApp')
        .factory('errorHandlerInterceptor', errorHandlerInterceptor);

    errorHandlerInterceptor.$inject = ['$q', '$rootScope'];

    function errorHandlerInterceptor ($q, $rootScope) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError (response) {
            if (!(response.status === 401 && (response.data === '' || (response.data.path && response.data.path.indexOf('/api/v1/account') === 0 )))) {
                $rootScope.$emit('sekcApp.httpError', response);
            }
            return $q.reject(response);
        }
    }
})();
