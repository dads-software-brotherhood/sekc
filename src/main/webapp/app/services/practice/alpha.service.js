(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('Alpha', Alpha);

    Alpha.$inject = ['$resource'];

    function Alpha ($resource) {
        var service = $resource('api/alpha/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();
