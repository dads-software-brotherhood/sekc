(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementConditionsController', PracticeManagementConditionsController);

    PracticeManagementConditionsController.$inject = ['$window','$stateParams','JhiLanguageService','entity','localStorageService','$filter','$location'];

    function PracticeManagementConditionsController($window, $stateParams, JhiLanguageService, entity, localStorageService, $filter, $location) {
        var vm = this;

        vm.load = load;
        vm.practice = entity;
        vm.validate = validate;
        vm.error = false;

        //measure
        vm.deleteMeasure = deleteMeasure;
        vm.cleanMeasure = cleanMeasure;
        vm.addMeasure = addMeasure;

        //entries y results
        vm.addEntry = addEntry;
        vm.addResult = addResult;
        vm.deleteEntry = deleteEntry;
        vm.deleteResult = deleteResult;
        vm.cleanEntries = cleanEntries;
        vm.cleanResults = cleanResults;

        vm.alphaEntryFilter = alphaEntryFilter;
        vm.workProductsEntryFilter = workProductsEntryFilter;
        vm.alphaResultsFilter = alphaResultsFilter;
        vm.workResultsEntryFilter = workResultsEntryFilter;

        vm.save = save;
        vm.clean = clean;


        vm.load();


        function load() {
            if (vm.practice.id == null) {
                vm.practice = localStorageService.get('practiceInEdition');
            }
            if (angular.isUndefined(vm.practice.conditions) || vm.practice.conditions === null) {
                vm.practice.conditions = { measures: [] };
                vm.practice.conditions.entries = { alphaStates: [], workProductsLevelofDetail: [], otherConditions: [] };
                vm.practice.conditions.results = { alphaStates: [], workProductsLevelofDetail: [], otherConditions: [] };
            }

            vm.alphas = localStorageService.get('alphas');
            vm.workProducts = localStorageService.get('workproducts');
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        function addMeasure() {

            if (!vm.newMeasure)
                return;

            vm.practice.conditions.measures.push(vm.newMeasure);
            console.log(vm.practice.conditions.measures);
            vm.cleanMeasure();
        }

        function deleteMeasure(index) {
            vm.practice.conditions.measures.splice(index, 1);
        }


        function cleanMeasure() {
            vm.newMeasure = '';
            vm.indexMeasure = -1;
        }

        function addEntry() {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {

                vm.practice.conditions.entries.alphaStates.push({ description: vm.alpha.name + ' / ' + vm.alphaState.name, idAlpha: vm.alpha.id, idState: vm.alphaState.id });

            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.practice.conditions.entries.workProductsLevelofDetail.push({ description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name, idWorkProduct: vm.workProduct.id, idLevelOfDetail: vm.levelOfDetail.id });

            } else if (vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
                vm.anotherEntryCriteria != "") {

                vm.practice.conditions.entries.otherConditions.push(vm.anotherEntryCriteria);
            }
            vm.cleanEntries();

        }

        function addResult() {
            if (vm.alphaResult != null && vm.alphaResult != undefined &&
                vm.alphaStateResult != null && vm.alphaStateResult != undefined) {

                vm.practice.conditions.results.alphaStates.push({ description: vm.alphaResult.name + ' / ' + vm.alphaStateResult.name, idAlpha: vm.alphaResult.id, idState: vm.alphaStateResult.id });

            } else if (vm.workProductResult != null && vm.workProductResult != undefined &&
                vm.levelOfDetailResult != null && vm.levelOfDetailResult != undefined) {

                vm.practice.conditions.results.workProductsLevelofDetail.push({ description: vm.workProductResult.name + ' / ' + vm.levelOfDetailResult.name, idWorkProduct: vm.workProductResult.id, idLevelOfDetail: vm.levelOfDetailResult.id });

            } else if (vm.anotherEntryCriteriaResult != null && vm.anotherEntryCriteriaResult != undefined) {

                vm.practice.conditions.results.otherConditions.push(vm.anotherEntryCriteriaResult);
            }
            vm.cleanResults();

        }

        function deleteEntry(index, tipo) {
            if (tipo == 'alpha') {
                vm.practice.conditions.entries.alphaStates.splice(index, 1);
            } else if (tipo == 'workproduct') {
                vm.practice.conditions.entries.workProductsLevelofDetail.splice(index, 1);
            } else if (tipo == 'other') {
                vm.practice.conditions.entries.otherConditions.splice(index, 1);
            }
        }

        function deleteResult(index, tipo) {
            if (tipo == 'alpha') {
                vm.practice.conditions.results.alphaStates.splice(index, 1);
            } else if (tipo == 'workproduct') {
                vm.practice.conditions.results.workProductsLevelofDetail.splice(index, 1);
            } else if (tipo == 'other') {
                vm.practice.conditions.results.otherConditions.splice(index, 1);
            }
        }

        function alphaEntryFilter(alphaState) {
            return $filter('filter')(vm.practice.conditions.entries.alphaStates, {
                idAlpha: vm.alpha.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsEntryFilter(levelOfDetails) {
            return $filter('filter')(vm.practice.conditions.entries.workProductsLevelofDetail, {
                idWorkProduct: vm.workProduct.id,
                idLevelOfDetail: levelOfDetails.id
            }).length == 0;
        }

        function alphaResultsFilter(alphaState) {
            return $filter('filter')(vm.practice.conditions.results.alphaStates, {
                idAlpha: vm.alphaResult.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workResultsEntryFilter(levelOfDetails) {
            return $filter('filter')(vm.practice.conditions.results.workProductsLevelofDetail, {
                idWorkProduct: vm.workProductResult.id,
                idLevelOfDetail: levelOfDetails.id
            }).length == 0;
        }

        function cleanEntries() {
            vm.alpha = null;
            vm.alphaState = null;
            vm.workProduct = null;
            vm.levelOfDetail = null;
            vm.anotherEntryCriteria = null;
        }

        function cleanResults() {
            vm.alphaResult = null;
            vm.alphaStateResult = null;
            vm.workProductResult = null;
            vm.levelOfDetailResult = null;
            vm.anotherEntryCriteriaResult = null;
        }

        function save() {
            if (vm.validate()) {
                localStorageService.set('practiceInEdition', vm.practice);
                $location.path('/practice-management/practiceThingswork/');
            } else {
                vm.error = true;
                vm.mensaje = "practiceManagement.error.1";
                $window.scrollTo(0, 0);
            }
            
        }

        function clean() {
            vm.practice.conditions = null;
            localStorageService.set('practiceInEdition', vm.practice);
            load();
        }

        function validate() {
            if (!vm.practice.conditions.measures ||
                (!vm.practice.conditions.entries.alphaStates.length &&
                    !vm.practice.conditions.entries.workProductsLevelofDetail.length &&
                    !vm.practice.conditions.entries.otherConditions.length) ||
                (!vm.practice.conditions.results.alphaStates.length &&
                    !vm.practice.conditions.results.workProductsLevelofDetail.length &&
                    !vm.practice.conditions.results.otherConditions.length)) {

                return false;
            }
            return true;
        }
    }


})();
