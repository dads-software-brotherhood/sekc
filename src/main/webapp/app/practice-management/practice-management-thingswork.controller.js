(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsWorkController', PracticeManagementThingsWorkController);

    PracticeManagementThingsWorkController.$inject = ['AlertService', '$filter', 'localStorageService', 'ivhTreeviewBfs', '$location', '$timeout'];

    function PracticeManagementThingsWorkController(AlertService, $filter, localStorageService, ivhTreeviewBfs, $location, $timeout) {
        var vm = this;

        vm.practice = null;
        vm.selectedWorkProducts = [];
        vm.load = load;
        vm.save = save;
        vm.clean = clean;
        vm.validate = validate;

        vm.alphasTree;
        vm.load();

        vm.error = false;
        vm.success = false;
        vm.close = close;

        function load() {
            var alphas = localStorageService.get('alphas');
            vm.practice = localStorageService.get('practiceInEdition');
            if (vm.practice == null) {
                vm.practice = {};
            }
            vm.alphasTree = [];
            if (vm.practice.thingsToWorkWith != null) {
                vm.selectedWorkProducts = vm.practice.thingsToWorkWith.workProducts;
            } else {
                vm.practice.thingsToWorkWith = { alphas: [], workProducts: [] };
            }
            fillTreeNode(vm.alphasTree, alphas, '');

        }

        function fillTreeNode(treeNode, alphas, parent) {
            angular.forEach(alphas, function(alpha) {
                if ((alpha.workproducts != null && alpha.workproducts.length > 0) ||
                    (alpha.subAlphas != null && alpha.subAlphas.length > 0)
                ) {
                    var alphaNode = {};
                    alphaNode.label = alpha.name;
                    alphaNode.value = parent + alpha.id;
                    alphaNode.id = alpha.id;
                    alphaNode.type = 'alpha';
                    alphaNode.children = [];
                    if (alpha.workproducts != null && alpha.workproducts.length > 0) {
                        angular.forEach(alpha.workproducts, function(workproduct) {
                            var wpNode = {
                                label: workproduct.name,
                                value: parent + alpha.id + '.' + workproduct.id,
                                type: 'workproduct',
                                id: workproduct.id,
                                selected: vm.selectedWorkProducts.indexOf(workproduct.id) > -1
                            }
                            alphaNode.children.push(wpNode);
                        });
                    }
                    if (alpha.subAlphas != null && alpha.subAlphas.length > 0) {
                        fillTreeNode(alphaNode.children, alpha.subAlphas, parent + alpha.id + '.')
                    }
                    treeNode.push(alphaNode);
                }
            });
        }

        function save() {
            vm.practice.thingsToWorkWith = { alphas: [], workProducts: [] };
            ivhTreeviewBfs(vm.alphasTree, function(node) {
                if (node.selected || node.__ivhTreeviewIndeterminate) {
                    if (node.type == 'alpha') {
                        vm.practice.thingsToWorkWith.alphas.push(node.id);
                    }
                    else if (node.type == 'workproduct') {
                        vm.practice.thingsToWorkWith.workProducts.push(node.id);
                    }
                }
            });
            if (vm.validate()) {
                localStorageService.set('practiceInEdition', vm.practice);
                vm.success = true;
                vm.mensaje = "practiceManagement.msg.4";
                vm.close('success', 2000);
            } else {
                vm.error = true;
                vm.mensaje = "practiceManagement.msg.2";
                vm.close('error', 3000);
                $window.scrollTo(0, 0);
            }
        }

        function validate() {
            var seleccionado = 0;
            ivhTreeviewBfs(vm.alphasTree, function(node) {
                if (node.selected || node.__ivhTreeviewIndeterminate) {
                    seleccionado += 1;
                }
            });
            if (seleccionado > 0) {
                return true;
            }
            return false;
        }

        function clean() {
            vm.practice.thingsToWorkWith = { alphas: [], workProducts: [] };
            localStorageService.set('practiceInEdition', vm.practice);

            ivhTreeviewBfs(vm.alphasTree, function(node) {
                if (node.selected) {
                    node.selected = false;
                }
            });
        }

        function close(msj, time) {
            if (msj == 'success') {
                $timeout(function() {
                    vm.success = false;
                    $location.path('/practice-management/thingsToDo/');
                }, time);
            } else {
                $timeout(function() {
                    vm.error = false;
                }, time);
            }
        }

    }

})();
