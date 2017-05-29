(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('Practice', Practice);

    Practice.$inject = ['$resource'];

    function Practice ($resource) {
        var service = $resource('api/practice', {}, {
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
