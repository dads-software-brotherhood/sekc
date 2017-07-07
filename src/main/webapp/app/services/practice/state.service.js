(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('State', State);

    State.$inject = ['$resource'];

    function State ($resource) {
        var service = $resource('api/state/:login', {}, {
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
