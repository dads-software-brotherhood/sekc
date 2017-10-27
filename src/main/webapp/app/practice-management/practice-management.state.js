(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('practice-management', {
                parent: 'app',
                url: '/practice-management?page&sort',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practiceManagement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management.html',
                        controller: 'PracticeManagementController',
                        controllerAs: 'vm'
                    }
                }, params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    }
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort)
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]

                }
            })
            .state('practice-management.new', {
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/practice-management/practice-management-dialog.html',
                        controller: 'PracticeManagementDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function() {
                                return {
                                    id: null, login: null, firstName: null, lastName: null, email: null,
                                    activated: true, langKey: null, createdBy: null, createdDate: null,
                                    lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                                    resetKey: null, authorities: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('practice-management', null, { reload: true });
                    }, function() {
                        $state.go('practice-management');
                    });
                }]
            })
            .state('practice-management.edit', {
                url: '/{login}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/practice-management/practice-management-dialog.html',
                        controller: 'PracticeManagementDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Practice', function(Practice) {
                                return Practice.get({ login: $stateParams.login });
                            }]
                        }
                    }).result.then(function() {
                        $state.go('practice-management', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('practice-management-detail', {
                parent: 'practice-management',
                url: '/practice/{login}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-detail.html',
                        controller: 'PracticeManagementDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-general', {
                parent: 'practice-management',
                url: '/practiceGeneral/{login}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-general.html',
                        controller: 'PracticeManagementGeneralController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function() {
                        return {
                            id: null, author: null, briefDescription: null, consistencyRules: null, description: null,
                            idKernel: null, name: null, objective: null, relatedPractices: null,
                            keywords: []
                        };
                    },
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-general.edit', {
                parent: 'practice-management',
                url: '/practiceGeneral/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-general.html',
                        controller: 'PracticeManagementGeneralController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$state', 'FindPractice', function($stateParams, $state, FindPractice) {
                        return FindPractice.get({ id: $stateParams.id }).$promise;
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-conditions', {
                parent: 'practice-management',
                url: '/practiceConditions/{login}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-conditions.html',
                        controller: 'PracticeManagementConditionsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function() {
                        return {
                            id: null, conditions: null
                        };
                    },
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-conditions.edit', {
                parent: 'practice-management',
                url: '/practiceConditions/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-conditions.html',
                        controller: 'PracticeManagementConditionsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$state', 'FindPractice', function($stateParams, $state, FindPractice) {
                        return FindPractice.get({ id: $stateParams.id }).$promise;
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-thingswork', {
                parent: 'practice-management',
                url: '/practiceThingswork/{login}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-thingswork.html',
                        controller: 'PracticeManagementThingsWorkController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('practice-management-thingsToDo', {
                parent: 'practice-management',
                url: '/thingsToDo/{login}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'practice-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/practice-management/practice-management-thingstodo.html',
                        controller: 'PracticeManagementThingsToDoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('practice-management');
                        return $translate.refresh();
                    }]
                }
            });
    }
})();
