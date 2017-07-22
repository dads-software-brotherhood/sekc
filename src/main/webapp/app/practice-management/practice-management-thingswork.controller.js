(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsWorkController', PracticeManagementThingsWorkController);

    PracticeManagementThingsWorkController.$inject = ['AlertService', '$filter', 'localStorageService', 'ivhTreeviewBfs'];

    function PracticeManagementThingsWorkController(AlertService, $filter, localStorageService, ivhTreeviewBfs) {
        var vm = this;
        vm.practice = null;
        vm.selectedWorkProducts = [];
        vm.load = load;
        vm.save = save;
        vm.alphasTree;
        vm.load();
        
        
        function load () {
            var alphas = localStorageService.get('alphas');
            vm.practice = localStorageService.get('practiceInEdition');
            vm.alphasTree = [];
            fillTreeNode(vm.alphasTree, alphas, '');
            console.log(vm.alphasTree);
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
                                id: workproduct.id
                            }
                            alphaNode.children.push(wpNode);
                            alphaNode.children.push({
                                label: 'P1',
                                value: parent + alpha.id + '.' + 'P1',
                                type: 'workproduct',
                                id: 'P1'
                            });
                            alphaNode.children.push({
                                label: 'P2',
                                value: parent + alpha.id + '.' + 'P2',
                                type: 'workproduct',
                                id: 'P2'
                            });
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
            vm.practice.thingsToWorkWith = { alphasSelection: [] };
            fillThingsToWorkWith(vm.practice.thingsToWorkWith.alphasSelection, vm.alphasTree, 0);
            localStorageService.set('practiceInEdition', vm.practice);
            console.log(vm.practice.thingsToWorkWith);
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
    }
        
})();
