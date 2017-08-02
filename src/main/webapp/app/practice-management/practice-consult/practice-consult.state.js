(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('practice-consult-menu', {
            parent: 'practice-management',
            url: '/practiceConsult/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-consult-menu.html',
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
        .state('practice-consult-alpha', {
            parent: 'practice-management',
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
            parent: 'practice-management',
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
            parent: 'practice-management',
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
            parent: 'practice-management',
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
            parent: 'practice-management',
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
            parent: 'practice-management',
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
        })
        .state('practice-management-spaceActivity', {
            parent: 'practice-management',
            url: '/spaceActivity/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult/practice-management-spaceActivity.html',
                    controller: 'PracticeManagementSpaceActivityController',
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
