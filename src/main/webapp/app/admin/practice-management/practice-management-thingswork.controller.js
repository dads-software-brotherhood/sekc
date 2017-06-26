(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsWorkController', PracticeManagementThingsWorkController);

    PracticeManagementThingsWorkController.$inject = ['Alpha', 'AlertService', '$filter'];

    function PracticeManagementThingsWorkController (Alpha, AlertService, $filter) {
        var vm = this;

        vm.load = load;
        vm.deleteEntryResult = deleteEntryResult;
        vm.addEntryResult = addEntryResult;
        vm.reset = reset;
        vm.update = update;
        
        vm.alphas = {};
        vm.indexMeasure = -1;
        vm.buttonAdd = "Add";
        vm.alphaWorkList = [];
        vm.entriesList = [];
        //solo pruebas
        vm.entries = [
        	{ name: 'Entry 1', id: 1},
            { name: 'Entry 2', id: 2}
        ];

        vm.resultsList = [];
        //solo pruebas
        vm.results = [
        	{ name: 'Result 1', id: 1},
            { name: 'Result 2', id: 2}
        ];
        vm.measures = [];
        
        vm.load();
        
        
        function load () {
            Alpha.query(onSuccess, onError);
        }
        
        function onSuccess(data, headers) {
            vm.alphas = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function deleteEntryResult (index, lista) {
           lista.splice(index, 1);
        }
        
        function addEntryResult (newItem, lista) {
            
            if (!newItem)
                return;
            
            vm.existe = $filter('filter')(lista, {id: newItem.id});
            if(vm.existe.length > 0)
                return;
            
            if(lista == 'measures')
            {
            	if(vm.indexMeasure == -1)
                {
                	vm.measures.push({name: newItem});
                }else{
                	vm.measures[vm.indexMeasure].name = newItem;
                }
                
            }else
            {
            	lista.push({
                    name: newItem.name,
                    id: newItem.id
                });
            }
            
            vm.existe = null;
            vm.reset();
        }
        
        function reset () {
            vm.entry = '';
            vm.result = '';
            vm.measure = '';
            vm.buttonAdd = "Add";
        }
        
        function update (index, item) {    		
	        vm.measure = item.name;
	        vm.indexMeasure = index;
	        vm.buttonAdd = "Save";
	        
    	}
    }
        
})();
