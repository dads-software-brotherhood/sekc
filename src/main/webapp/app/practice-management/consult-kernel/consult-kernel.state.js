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
        })
        .state('consult-kernel-competency', {
            parent: 'app',
            url: '/consultKernelCompetency/(login)',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/consult-kernel/consult-kernel-competency.html',
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
         .state('consult-kernel-activitySpace', {
            parent: 'app',
            url: '/consultKernelActivitySpace/(login)',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/consult-kernel/consult-kernel-activitySpace.html',
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
           .state('consult-kernel-areaOfConcern', {
            parent: 'app',
            url: '/consultKernelAreaOfConcern/(login)',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/consult-kernel/consult-kernel-areaOfConcern.html',
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
        });
    }
})();
