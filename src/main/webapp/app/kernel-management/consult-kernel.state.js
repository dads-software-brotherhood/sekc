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
            url: '/consultAlphas',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kernel-management/alphas-management/consult-kernel-alphas.html',
                    controller: 'ConsultKernelAlphasController',
                    controllerAs: 'vm'
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
            url: '/consultKernelWorkproducts',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kernel-management/workproduct-management/consult-kernel-workproducts.html',
                    controller: 'ConsultKernelWorkproductsController',
                    controllerAs: 'vm'
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
            url: '/consultKernelCompetency',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kernel-management/competences-management/consult-kernel-competency.html',
                    controller: 'ConsultKernelCompetenciesController',
                    controllerAs: 'vm'
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
            url: '/consultKernelActivitySpace',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kernel-management/activityspace-management/consult-kernel-activitySpace.html',
                    controller: 'ConsultKernelActivitySpaceController',
                    controllerAs: 'vm'
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
            url: '/consultKernelAreaOfConcern',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kernel-management/consult-kernel-areaOfConcern.html',
                    controller: 'ConsultKernelAreaOfConcernController',
                    controllerAs: 'vm'
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
