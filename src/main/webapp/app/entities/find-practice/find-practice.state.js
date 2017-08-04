(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('find-practice', {
            parent: 'entity',
            url: '/find-practice?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'find-practice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/find-practice/findpractice.html',
                    controller: 'FindPracticeController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('find-practice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('find-practice-detail', {
            parent: 'find-practice',
            url: '/find-practice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'find-practice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/find-practice/find-practice-detail.html',
                    controller: 'FindPracticeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('find-practice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FindPractice', function($stateParams, FindPractice) {
                    return FindPractice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'find-practice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('find-practice-detail.edit', {
            parent: 'find-practice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/find-practice/find-practice-dialog.html',
                    controller: 'FindPracticeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FindPractice', function(FindPractice) {
                            return FindPractice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('find-practice.new', {
            parent: 'find-practice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/find-practice/find-practice-dialog.html',
                    controller: 'FindPracticeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                url: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('find-practice', null, { reload: 'find-practice' });
                }, function() {
                    $state.go('find-practice');
                });
            }]
        })
        .state('find-practice.edit', {
            parent: 'find-practice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/find-practice/find-practice-dialog.html',
                    controller: 'FindPracticeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FindPractice', function(FindPractice) {
                            return FindPractice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('find-practice', null, { reload: 'find-practice' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('find-practice.delete', {
            parent: 'find-practice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/find-practice/find-practice-delete-dialog.html',
                    controller: 'FindPracticeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FindPractice', function(FindPractice) {
                            return FindPractice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('find-practice', null, { reload: 'find-practice' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
