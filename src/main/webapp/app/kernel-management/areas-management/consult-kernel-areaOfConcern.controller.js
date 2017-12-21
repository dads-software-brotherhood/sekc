(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelAreaOfConcernController', ConsultKernelAreaOfConcernController);

    ConsultKernelAreaOfConcernController.$inject = ['$stateParams', 'PracticeCatalogs', 'AlertService', 'localStorageService'];

    function ConsultKernelAreaOfConcernController($stateParams, PracticeCatalogs, AlertService, localStorageService) {
        var vm = this;

        vm.practice = null;
        vm.load = load;

        vm.load();


        function load() {
            if (angular.isUndefined(localStorageService.get('areasOfConcern')) ||
                localStorageService.get('areasOfConcern') == null) {
                PracticeCatalogs.query(onSuccess, onError);
            } else {
                vm.areasOfConcern = localStorageService.get('areasOfConcern');
            }
        }

        function onSuccess(data, headers) {
            localStorageService.set('areasOfConcern', data.catalogs.areasOfConcern);
            vm.areasOfConcern = localStorageService.get('areasOfConcern');
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }
    }
})();
