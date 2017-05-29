(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementController', PracticeManagementController);

    PracticeManagementController.$inject = ['Principal', 'Practice', 'ParseLinks', 'AlertService', '$state', 'pagingParams', 'paginationConstants', 'JhiLanguageService'];

    function PracticeManagementController(Principal, Practice, ParseLinks, AlertService, $state, pagingParams, paginationConstants, JhiLanguageService) {
        var vm = this;

        vm.currentAccount = null;
        vm.setActive = setActive;
        vm.practices = [];
        vm.page = 1;
        vm.totalItems = null;
        vm.clear = clear;
        vm.links = null;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.transition = transition;

        
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function setActive (practice, isActivated) {
            practice.activated = isActivated;
            Practice.update(practice, function () {
                vm.loadAll();
                vm.clear();
            });
        }

        function loadAll () {
            Practice.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
        }

        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.practices = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        function clear () {
            vm.practice = {
                id: null, name: null, icon: null, briefDescription: null, description: null,
                consistencyRules: null, objective: null, measures: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
        }

        function sort () {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
       
    }
})();
