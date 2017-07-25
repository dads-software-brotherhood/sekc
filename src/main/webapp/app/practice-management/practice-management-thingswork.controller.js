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
            vm.alphasTree = [];
            if (vm.practice.thingsToWorkWith != null)
            {
                fillSelectedWorkproducts(vm.practice.thingsToWorkWith.alphasSelection, '');
            }
            console.log(vm.selectedWorkProducts);
            fillTreeNode(vm.alphasTree, alphas, '');
            console.log(vm.alphasTree);
        }

        function fillSelectedWorkproducts(alphas, father)
        {
            angular.forEach(alphas, function (alpha) {
                angular.forEach(alpha.workProducts, function (workProduct) {
                    vm.selectedWorkProducts.push(father + alpha.idAlpha + '.' + workProduct);
                });
                fillSelectedWorkproducts(alpha.subAlphas, father + alpha.id + '.');
            });
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
                                selected: vm.selectedWorkProducts.indexOf(parent + alpha.id + '.' + workproduct.id) > -1
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
        

        function fillThingsToWorkWith(node, nodeOrigin, nivel)
        {
            angular.forEach(nodeOrigin, function (nodeSelected) {
                if (nodeSelected.selected || nodeSelected.__ivhTreeviewIndeterminate)
                {
                    if (nodeSelected.type == 'alpha')
                    {
                        var newNode = { idAlpha: nodeSelected.id, subAlphas: [], workProducts: [] };
                        if (nivel == 0)
                            node.push(newNode);
                        else
                            node.subAlphas.push(newNode);
                        fillThingsToWorkWith(newNode, nodeSelected.children, nivel + 1)
                    }
                    else if (nodeSelected.type == 'workproduct')
                    {
                        node.workProducts.push(nodeSelected.id);
                    }
                }
            });
        }

        function save()
        {
            vm.practice.thingsToWorkWith = { alphasSelection: [] };
            fillThingsToWorkWith(vm.practice.thingsToWorkWith.alphasSelection, vm.alphasTree, 0);
            localStorageService.set('practiceInEdition', vm.practice);
            console.log($location.path());
	        $location.path('/practice-management/spaceActivity/');
        }
        
        function clean() {
        	vm.practice.thingsToWorkWith = null;
        	localStorageService.set('practiceInEdition', vm.practice);
        	load();
    	}

    }
        
})();
