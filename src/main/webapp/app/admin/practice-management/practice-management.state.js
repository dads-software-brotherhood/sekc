(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('practice-management', {
            parent: 'admin',
            url: '/practice-management?page&sort',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'practiceManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/practice-management/practice-management.html',
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
                    templateUrl: 'app/admin/practice-management/practice-management-dialog.html',
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
                    templateUrl: 'app/admin/practice-management/practice-management-dialog.html',
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
                    templateUrl: 'app/admin/practice-management/practice-management-detail.html',
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
        .state('practice-management.delete', {
            url: '/{login}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/practice-management/practice-management-delete-dialog.html',
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
        });
    }
})();
