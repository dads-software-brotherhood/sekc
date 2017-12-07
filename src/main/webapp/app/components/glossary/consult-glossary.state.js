(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('consult-glossary', {
                parent: 'app',
                url: '/consultGlossary',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'glossary.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/components/glossary/consult-glossary.html',
                        controller: 'ConsultGlossaryController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('glossary');
                        return $translate.refresh();
                    }]

                }
            });
    }
})();
