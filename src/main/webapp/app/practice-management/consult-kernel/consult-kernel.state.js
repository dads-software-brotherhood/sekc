(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consult-kernel-alphas', {
            parent: 'app',
            url: '/consultKernel?page&sort',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/consult-kernel/consult-kernel-alphas.html',
                    //controller: 'PracticeManagementController',
                    //controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('practice-management');
                    return $translate.refresh();
                }]

            }        
        })
        .state('consult-kernel-workproducts', {
            parent: 'app',
            url: '/consultKernelWorkproducts/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/consult-kernel/consult-kernel-workproducts.html',
                    //controller: 'PracticeManagementThingsWorkController',
                    //controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('practice-management');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
