(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsWorkController', PracticeManagementThingsWorkController);

    PracticeManagementThingsWorkController.$inject = ['AlertService', '$filter', 'localStorageService', 'ivhTreeviewBfs'];

    function PracticeManagementThingsWorkController(AlertService, $filter, localStorageService, ivhTreeviewBfs) {
        var vm = this;

        vm.load = load;
        vm.save = save;
        vm.alphasTree;
//        vm.deleteEntryResult = deleteEntryResult;
//        vm.addEntryResult = addEntryResult;
//        vm.reset = reset;
//        vm.update = update;
//        
//        vm.alphas = {};
//        vm.indexMeasure = -1;
//        vm.buttonAdd = "Add";
//        vm.alphaWorkList = [];
//        vm.entriesList = [];
//        //solo pruebas
//        vm.entries = [
//        	{ name: 'Entry 1', id: 1},
//            { name: 'Entry 2', id: 2}
//        ];
//
//        vm.resultsList = [];
//        //solo pruebas
//        vm.results = [
//        	{ name: 'Result 1', id: 1},
//            { name: 'Result 2', id: 2}
//        ];
//        vm.measures = [];
//        
        vm.load();
        
        
        function load () {
            var alphas = localStorageService.get('alphas');
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
                    alphaNode.type = 'alpha';
                    alphaNode.children = [];
                    if (alpha.workproducts != null && alpha.workproducts.length > 0)
                    {
                        angular.forEach(alpha.workproducts, function (workproduct) {
                            var wpNode = {
                                label: workproduct.name,
                                value: parent + alpha.id + '.' + workproduct.id,
                                type: 'workproduct'
                            }
                            alphaNode.children.push(wpNode);
                            alphaNode.children.push({
                                label: 'P1',
                                value: parent + alpha.id + '.' + 'P1',
                                type: 'workproduct'
                            });
                            alphaNode.children.push({
                                label: 'P2',
                                value: parent + alpha.id + '.' + 'P2',
                                type: 'workproduct'
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
            var selectedNodes = []
            ivhTreeviewBfs(vm.alphasTree, function (node) {
                if (node.selected) {
                    selectedNodes.push(node)
                }
            })

            console.log(selectedNodes);
        }
//        
//        function onSuccess(data, headers) {
//            vm.alphas = data;
//        }
//
//        function onError(error) {
//            AlertService.error(error.data.message);
//        }
//        
//        function deleteEntryResult (index, lista) {
//           lista.splice(index, 1);
//        }
//        
//        function addEntryResult (newItem, lista) {
//            
//            if (!newItem)
//                return;
//            
//            vm.existe = $filter('filter')(lista, {id: newItem.id});
//            if(vm.existe.length > 0)
//                return;
//            
//            if(lista == 'measures')
//            {
//            	if(vm.indexMeasure == -1)
//                {
//                	vm.measures.push({name: newItem});
//                }else{
//                	vm.measures[vm.indexMeasure].name = newItem;
//                }
//                
//            }else
//            {
//            	lista.push({
//                    name: newItem.name,
//                    id: newItem.id
//                });
//            }
//            
//            vm.existe = null;
//            vm.reset();
//        }
//        
//        function reset () {
//            vm.entry = '';
//            vm.result = '';
//            vm.measure = '';
//            vm.buttonAdd = "Add";
//        }
//        
//        function update (index, item) {    		
//	        vm.measure = item.name;
//	        vm.indexMeasure = index;
//	        vm.buttonAdd = "Save";
//	        
//    	}
    }
        
})();
