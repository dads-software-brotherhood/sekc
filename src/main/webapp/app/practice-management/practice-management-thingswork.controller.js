(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsWorkController', PracticeManagementThingsWorkController);

    PracticeManagementThingsWorkController.$inject = ['AlertService', '$filter', 'localStorageService', 'ivhTreeviewBfs', '$location'];

    function PracticeManagementThingsWorkController(AlertService, $filter, localStorageService, ivhTreeviewBfs, $location) {
        var vm = this;
        
        vm.practice = null;
        vm.selectedWorkProducts = [];
        vm.load = load;
        vm.save = save;
        vm.clean = clean;
        
        vm.alphasTree;
        vm.load();
        
        
        function load () {
            var alphas = localStorageService.get('alphas');
            vm.practice = localStorageService.get('practiceInEdition');
            if (vm.practice == null)
            {
                vm.practice = {};
            }
            vm.alphasTree = [];
            if (vm.practice.thingsToWorkWith != null)
            {
                vm.selectedWorkProducts = vm.practice.thingsToWorkWith.workProducts;
            }
            fillTreeNode(vm.alphasTree, alphas, '');
            
        }

        

        function fillTreeNode(treeNode, alphas, parent)
        {
            angular.forEach(alphas, function (alpha) {
                if ((alpha.workproducts != null && alpha.workproducts.length > 0) ||
                    (alpha.subAlphas != null && alpha.subAlphas.length > 0)
                )
                {
                    var alphaNode = {};
                    alphaNode.label = alpha.name;
                    alphaNode.value = parent + alpha.id;
                    alphaNode.id = alpha.id;
                    alphaNode.type = 'alpha';
                    alphaNode.children = [];
                    if (alpha.workproducts != null && alpha.workproducts.length > 0)
                    {
                        angular.forEach(alpha.workproducts, function (workproduct) {
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
                    if (alpha.subAlphas != null && alpha.subAlphas.length > 0)
                    {
                        fillTreeNode(alphaNode.children, alpha.subAlphas, parent + alpha.id + '.')
                    }
                    treeNode.push(alphaNode);
                }
            });
        }
        

        function save()
        {
        	console.log(vm.model);
            vm.practice.thingsToWorkWith = { alphas: [], workProducts:[] };

            ivhTreeviewBfs(vm.alphasTree, function (node) {
                if (node.selected || node.__ivhTreeviewIndeterminate) {
                    if (node.type == 'alpha') {
                        vm.practice.thingsToWorkWith.alphas.push(node.id);
                    }
                    else if (node.type == 'workproduct') {
                        vm.practice.thingsToWorkWith.workProducts.push(node.id);
                    }
                }
            });

            localStorageService.set('practiceInEdition', vm.practice);
	        $location.path('/practice-management/thingsToDo/');
        }
        
        function clean() {
            vm.practice.thingsToWorkWith = { alphas: [], workProducts: [] };
            localStorageService.set('practiceInEdition', vm.practice);

            ivhTreeviewBfs(vm.alphasTree, function (node) {
                if (node.selected) {
                    node.selected = false;
                }
            });

    	}

    }
        
})();
