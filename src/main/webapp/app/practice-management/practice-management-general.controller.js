(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementGeneralController', PracticeManagementGeneralController);

    PracticeManagementGeneralController.$inject = ['$scope', '$window', '$stateParams', 'AlertService', 'JhiLanguageService', 'entity', 'localStorageService', 'PracticeCatalogs', '$location', '$timeout'];

    function PracticeManagementGeneralController($scope, $window, $stateParams, AlertService, JhiLanguageService, entity, localStorageService, PracticeCatalogs, $location, $timeout) {
        var vm = this;

        vm.load = load;
        vm.clean = clean;
        vm.validate = validate;
        vm.practice = entity;
        vm.indexKeyword = -1;

        vm.deleteKeyword = deleteKeyword;
        vm.clearKeyword = clearKeyword;
        vm.addKeyword = addKeyword;
        vm.saveLocal = saveLocal;
        vm.save = save;

        vm.error = false;
        vm.success = false;
        vm.close = close;

        vm.load();

        function load() {
            PracticeCatalogs.query(onSuccess, onError);
        }

        function onSuccess(data, headers) {
            if (vm.practice.id == null) {
                vm.practice = localStorageService.get('practiceInEdition');
                if (angular.isUndefined(vm.practice) || vm.practice === null) {
                    vm.practice = entity;
                }
            }

            localStorageService.set('actionsKinds', data.catalogs.actionsKinds);
            localStorageService.set('kernels', data.catalogs.kernels);
            localStorageService.set('activitySpaces', data.catalogs.activitySpaces);
            localStorageService.set('areasOfConcern', data.catalogs.areasOfConcern);
            localStorageService.set('alphas', data.catalogs.alphas);
            localStorageService.set('workproducts', data.catalogs.workproducts);
            localStorageService.set('competencies', data.catalogs.competencies);
            localStorageService.set('resourcesTypes', data.catalogs.resourcesTypes);
            if (localStorageService.get('practices')) {
                localStorageService.remove('practices');
            }
            localStorageService.set('practices', data.catalogs.practices);

            vm.kernels = localStorageService.get('kernels');
            vm.relatedPractices = localStorageService.get('practices');
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        function addKeyword() {
            if (!vm.newKeyword)
                return;

            if (vm.indexKeyword == -1) {
                vm.practice.keywords.push(vm.newKeyword);
            } else {
                vm.practice.keywords[vm.indexKeyword].keyWord = vm.newKeyword;
            }
            console.log(vm.practice.keywords);
            vm.clearKeyword();
        }

        function deleteKeyword(index) {
            vm.practice.keywords.splice(index, 1);
        }

        function clearKeyword() {
            vm.newKeyword = '';
            vm.indexKeyword = -1;
        }

        function save() {
            if (vm.validate()) {
                vm.saveLocal();
                vm.success = true;
                vm.mensaje = "practiceManagement.msg.4";
                vm.close('success', 2000);
            } else {
                vm.error = true;
                vm.mensaje = "practiceManagement.msg.1";
                vm.close('error', 3000);
                $window.scrollTo(0, 0);
            }
        }

        function saveLocal() {
            localStorageService.set('practiceInEdition', vm.practice);
        }

        function clean() {
            vm.practice = entity;
            localStorageService.set('practiceInEdition', null);
        }

        function validate() {
            if (!vm.practice.idKernel ||
                !vm.practice.name ||
                !vm.practice.author ||
                !vm.practice.objective ||
                !vm.practice.briefDescription ||
                !vm.practice.description) {

                return false;
            }
            return true;
        }

        function close(msj, time) {
            if (msj == 'success') {
                $timeout(function() {
                    vm.success = false;
                    $location.path('/practice-management/practiceConditions/');
                }, time);
            } else {
                $timeout(function() {
                    vm.error = false;
                }, time);
            }
        }
    }

})();
