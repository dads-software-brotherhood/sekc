(function () {
    'use strict';

    angular
        .module('sekcApp')
        .factory('GlossaryService', GlossaryService);

    GlossaryService.$inject = ['$http', '$location'];

    function GlossaryService($http, $location) {

        var dataPromise;

        var service = {
            getGlossary: getGlossary
        };

        return service;

        function getGlossary() {
            if (angular.isUndefined(dataPromise)) {
                dataPromise = $http.get('/app/components/glossary/glossary.json').then(function (result) {
                    if (result.data) {
                        return result;
                    }
                });
            }
            return dataPromise;
        }
    }
})();
