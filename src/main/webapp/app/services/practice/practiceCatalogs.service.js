(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('PracticeCatalogs', PracticeCatalogs);

    PracticeCatalogs.$inject = ['$resource'];

    function PracticeCatalogs ($resource) {
        var service = $resource('api/v1/catalogs', {}, {
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
