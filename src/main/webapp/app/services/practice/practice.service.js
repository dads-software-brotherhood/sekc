(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('Practice', Practice);

    Practice.$inject = ['$resource'];

    function Practice ($resource) {
        var service = $resource('api/v1/practices/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                	 data = angular.isObject(data);
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
