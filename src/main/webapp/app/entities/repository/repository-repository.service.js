(function() {
    'use strict';
    angular
        .module('sekcApp')
        .factory('Repository', Repository);

    Repository.$inject = ['$resource'];

    function Repository ($resource) {
        var resourceUrl =  'api/v1/repositories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
