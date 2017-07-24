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
            },            params: {
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
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort)
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('practice-management');
                    return $translate.refresh();
                }]

            }        })
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
                        entity: function () {
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
                            return Practice.get({login : $stateParams.login});
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('practice-management');
                    return $translate.refresh();
                }]
            }
        })
        .state('practice-management-activity', {
            parent: 'practice-management',
            url: '/practicesActivities/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.spaceActivity.title'
            },
            views: {
                'content@': {
                	templateUrl: 'app/practice-management/practice-management-activity.html',
                    //controller: 'PracticeManagementSpaceActivityController',
                    //controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('activities');
                    return $translate.refresh();
                }]
            }
        })
        .state('practice-management.delete', {
            url: '/{login}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-delete-dialog.html',
                    controller: 'PracticeManagementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Practice', function(Practice) {
                            return Practice.get({login : $stateParams.login});
                        }]
                    }
                }).result.then(function() {
                    $state.go('practice-management', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('practice-management');
                    return $translate.refresh();
                }]
            }
        })
            .state('practice-consult-menu', {
            parent: 'practice-management',
            url: '/practiceConsult/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-consult-menu.html',
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
         .state('practice-management-completition', {
            parent: 'practice-management',
            url: '/practiceCompletition/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-management-completition.html',
                    controller: 'PracticeManagementCompletionController',
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
        .state('practice-management-spaceActivity', {
            parent: 'practice-management',
            url: '/spaceActivity/{login}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practice-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/practice-management/practice-management-spaceActivity.html',
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
        })
        .state('practice-management-spaceActivity.action', {
            url: '/action',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-action-dialog.html',
                    controller: 'PracticeManagementActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        })
        .state('practice-management-spaceActivity.competency', {
            url: '/competency',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-competency-dialog.html',
                    //controller: 'PracticeManagementApproachDialogController',
                    //controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        })
        .state('practice-management-spaceActivity.approach', {
            url: '/approach',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-approach-dialog.html',
                    controller: 'PracticeManagementApproachDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        })
        .state('practice-management-spaceActivity.completition', {
            url: '/completition',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-completition-dialog.html',
                    controller: 'PracticeManagementCompletitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        })
        .state('practice-management-spaceActivity.resources', {
            url: '/resources',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-resources-dialog.html',
                    controller: 'PracticeManagementResourcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        })
        .state('practice-management-spaceActivity.entry', {
            url: '/entry',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/practice-management/practice-management-entry-dialog.html',
                    controller: 'PracticeManagementEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static'
                }).result.then(function() {
                    $state.go('practice-management-spaceActivity', null, { reload: true });
                }, function() {
                    $state.go('practice-management-spaceActivity');
                });
            }]
        });
    }
})();
