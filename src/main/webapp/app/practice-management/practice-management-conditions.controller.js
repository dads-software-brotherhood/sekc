(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementConditionsController',PracticeManagementConditionsController);

    PracticeManagementConditionsController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementConditionsController ($stateParams, JhiLanguageService, localStorageService) {
        var vm = this;

        vm.load = load;
        vm.indexMeasure = -1;
        vm.measures = [];
        vm.entries = [];
        vm.results= [];
        vm.entriesTable = [];
        vm.resultsTable = [];
        
        //measure
        vm.deleteMeasure = deleteMeasure;
        vm.cleanMeasure = cleanMeasure;
        vm.addMeasure = addMeasure;
        
        //entries y results
        vm.getChilds = getChilds;
        vm.combos = combos;
        vm.validaCombos = validaCombos;
        vm.cleanCombos = cleanCombos;
        
        vm.cleanAlpha = cleanAlpha;
    	vm.cleanWorkProduct = cleanWorkProduct;
    	vm.cleanAnother = cleanAnother;
    	
    	vm.obtenerEstados = obtenerEstados;
        vm.addItem = addItem;
        vm.deleteItem = deleteItem;
        
        vm.save = save;
        vm.cancel = cancel;
        
		
        vm.load();
        vm.cleanCombos(0);
        
        function load () {
        	vm.alphas = localStorageService.get('alphas');
        	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function addMeasure() {
    		
            if (!vm.newMeasure)
                return;
            
            if(vm.indexMeasure == -1)
            {
            	vm.measures.push({name: vm.newMeasure});
            }else{
            	vm.measures[vm.indexMeasure].measure = vm.newMeasure;
            }
            console.log(vm.measures);
            vm.cleanMeasure();
    	}
        
        function deleteMeasure (index) {
            vm.measures.splice(index, 1);
        }
    	
        
    	function cleanMeasure () {
             vm.newMeasure = '';
             vm.indexMeasure = -1;
        }

        function combos (tipo) {
        	if(tipo == 0){
        		vm.disabledWork = false;
        		vm.disabledAnother = false;
        		vm.disabledAlpha = false;
        		vm.disabledWorkR = false;
        		vm.disabledAnotherR = false;
        		vm.disabledAlphaR = false;
        	}else if(tipo == 1){
        		vm.disabledWork = false;
        		vm.disabledAnother = false;
        		vm.disabledAlpha = false;
        	}else if(tipo == 2){
        		vm.disabledWorkR = false;
        		vm.disabledAnotherR = false;
        		vm.disabledAlphaR = false;
        	}
        	
        	vm.cleanCombos(tipo);
        	
        }
        
        function cleanCombos (tipo) {
        	vm.cleanAlpha(tipo);
        	vm.cleanWorkProduct(tipo);
        	vm.cleanAnother(tipo);
        }
        
        function cleanAlpha(tipo){
        	if(tipo == 1){
            	vm.alpha = '';
            	vm.alphaState = '';
        	}else if(tipo == 2){
            	vm.alphaResult = '';
            	vm.alphaStateResult = '';
        	}
        }
        
        function cleanWorkProduct(tipo){
        	if(tipo == 1){
            	vm.workProduct = '';
            	vm.levelOfDetail = '';
        	}else if(tipo == 2){
            	vm.workProductResult = '';
            	vm.levelOfDetailResult = '';
        	}
        }
        
        function cleanAnother(tipo){
        	if(tipo == 1){
        		vm.anotherEntryCriteria = '';
        	}else if(tipo == 2){
        		vm.anotherEntryCriteriaResult = '';
        	}
        }
        
        function getChilds (padre, valor) {
        	
        	vm.validaCombos(padre, valor);
        	
        }
        
        function validaCombos(padre, tipo){
    		if(tipo == 1){
    			if(padre == 'alpha'){
            		vm.disabledWork = true;
            		vm.disabledAnother = true;
            		//vm.obtenerEstados(vm.alpha.id);
            	}else if(padre == 'workproduct'){
            		vm.disabledAlpha = true;
            		vm.disabledAnother = true;
            	}else if(padre == 'another'){
            		vm.disabledAlpha = true;
            		vm.disabledWork = true;
            	}
    		}else if(tipo == 2){
    			if(padre == 'alphaR'){
            		vm.disabledWorkR = true;
            		vm.disabledAnotherR = true;
            	}else if(padre == 'workproductR'){
            		vm.disabledAlphaR = true;
            		vm.disabledAnotherR = true;
            	}else if(padre == 'anotherR'){
            		vm.disabledAlphaR = true;
            		vm.disabledWorkR = true;
            	}
    		}
    			
        }
        
        function obtenerEstados(){
        	//vm.estados = $filter('filter')(vm.alphas, { id: vm.alpha.id});
        }
        
        function addItem (tipo) {
//        	if(tipo == 1){
//        		if(true){
//        			if(!vm.states){
//        				return;
//        			}
//            		vm.entriesTable.push({description: padre +' / '+ hijo});
//        		}else if(padre == 'workproduct'){
//        			if(!vm.levelOfDetail){
//        				return;
//        			}
//            		vm.entriesTable.push({description: padre +' / '+ hijo});
//        		}else if(padre == 'another'){
//        			vm.entriesTable.push({description: padre});
//        		}
//        	}else if(tipo == 2){
//        		vm.resultsTable.push({description: padre +' / '+ hijo});
//        	}
        	
            //console.log("entriesTable"+vm.entriesTable);
    	
        }
        
    	function save () {
//           console.log(vm.kernel + " kernel");
//           console.log(vm.kernels + " kernels");
        }
    	
    	function deleteItem () {
    		
    	}
    	
    	function cancel () {
    		//localStorageService.remove('practica')
    		//regresar consultar prácticas
    	}
	}
        
    
})();
