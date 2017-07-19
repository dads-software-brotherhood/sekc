(function() {
    'use strict';

    angular
        .module('sekcApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('repository-repository', {
            parent: 'entity',
            url: '/repository-repository?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'repository.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/repository/repositoriesrepository.html',
                    controller: 'RepositoryRepositoryController',
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
                    $translatePartialLoader.addPart('repository');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('repository-repository-detail', {
            parent: 'repository-repository',
            url: '/repository-repository/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'repository.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/repository/repository-repository-detail.html',
                    controller: 'RepositoryRepositoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('repository');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Repository', function($stateParams, Repository) {
                    return Repository.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'repository-repository',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('repository-repository-detail.edit', {
            parent: 'repository-repository-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repository/repository-repository-dialog.html',
                    controller: 'RepositoryRepositoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Repository', function(Repository) {
                            return Repository.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('repository-repository.new', {
            parent: 'repository-repository',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repository/repository-repository-dialog.html',
                    controller: 'RepositoryRepositoryDialogController',
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
                    $state.go('repository-repository', null, { reload: 'repository-repository' });
                }, function() {
                    $state.go('repository-repository');
                });
            }]
        })
        .state('repository-repository.edit', {
            parent: 'repository-repository',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repository/repository-repository-dialog.html',
                    controller: 'RepositoryRepositoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Repository', function(Repository) {
                            return Repository.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('repository-repository', null, { reload: 'repository-repository' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('repository-repository.delete', {
            parent: 'repository-repository',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repository/repository-repository-delete-dialog.html',
                    controller: 'RepositoryRepositoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Repository', function(Repository) {
                            return Repository.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('repository-repository', null, { reload: 'repository-repository' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
