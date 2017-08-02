(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('practice-consult-menu', {
            parent: 'app',
            url: '/practiceConsult?page&sort',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-menu.html',
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
        .state('practice-consult-alpha', {
            parent: 'app',
            url: '/practiceConsultAlpha/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-alpha.html',
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
         .state('practice-consult-workproduct', {
            parent: 'app',
            url: '/practiceConsultWorkproduct/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-workproduct.html',
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
          .state('practice-consult-ActivitySpace', {
            parent: 'app',
            url: '/practiceConsultSpaceActiviy/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-ActivitySpace.html',
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
         .state('practice-consult-activity', {
            parent: 'app',
            url: '/practiceConsultActiviy/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-activity.html',
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
           .state('practice-consult-competency', {
            parent: 'app',
            url: '/practiceConsultCompetency/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-competency.html',
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
        .state('practice-consult-areaOfConcern', {
            parent: 'app',
            url: '/practiceConsultAreaOfConcern/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-areaOfConcern.html',
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
