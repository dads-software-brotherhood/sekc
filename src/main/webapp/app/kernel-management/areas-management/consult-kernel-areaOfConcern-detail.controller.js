(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelAreaOfConcernDetailController', ConsultKernelAreaOfConcernDetailController);

    ConsultKernelAreaOfConcernDetailController.$inject = ['$stateParams', 'localStorageService', '$filter'];

    function ConsultKernelAreaOfConcernDetailController($stateParams, localStorageService, $filter) {
        var vm = this;

        vm.area = $stateParams.area;
        vm.load = load;
        vm.load();


        function load() {
            if (angular.isUndefined(localStorageService.get('areasOfConcern')) ||
                localStorageService.get('areasOfConcern') == null) {
                PracticeCatalogs.query(onSuccess, onError);
            } else {
                vm.areaOfConcern = $filter('filter')(localStorageService.get('areasOfConcern'), { id: vm.area })[0];
            }
        }

        function onSuccess(data, headers) {
            localStorageService.set('areasOfConcern', data.catalogs.areasOfConcern);
            vm.areaOfConcern = $filter('filter')(localStorageService.get('areasOfConcern'), { id: vm.area })[0];
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }
    }
})();
