(function() {
    'use strict';
    angular
        .module('sekcApp')
        .factory('FindPractice', FindPractice);

    FindPractice.$inject = ['$resource'];

    function FindPractice ($resource) {
        var resourceUrl =  'api/v1/practices/:id';

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
