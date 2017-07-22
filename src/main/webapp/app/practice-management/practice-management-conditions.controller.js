(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementConditionsController',PracticeManagementConditionsController);

    PracticeManagementConditionsController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter'];

    function PracticeManagementConditionsController ($stateParams, JhiLanguageService, localStorageService, $filter) {
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
        vm.disabledCombos = disabledCombos;
        vm.cleanCombos = cleanCombos;
    	vm.validaCombos = validaCombos;
        
        vm.cleanAlpha = cleanAlpha;
    	vm.cleanWorkProduct = cleanWorkProduct;
    	vm.cleanAnother = cleanAnother;
    	
    	vm.getChilds = getChilds;
        vm.addItem = addItem;
        vm.deleteItem = deleteItem;
        
        vm.save = save;
        vm.cancel = cancel;
        
		
        vm.load();
        vm.cleanCombos(0);
        
        function load () {
        	vm.alphas = localStorageService.get('alphas');
        	vm.workproducts = localStorageService.get('workproducts');
        	
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
        		vm.visibleAlpha = false;
        		vm.visibleWorkproduct = false;
        	}else if(tipo == 2){
        		vm.disabledWorkR = false;
        		vm.disabledAnotherR = false;
        		vm.disabledAlphaR = false;
        		vm.visibleAlphaR = false;
        		vm.visibleWorkproductR = false;
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
        
        function validaCombos (padre, valor) {
        	
        	vm.disabledCombos(padre, valor);
        	
        }
        
        function disabledCombos(padre, tipo){
    		if(tipo == 1){
    			if(padre == 'alpha'){
            		vm.disabledWork = true;
            		vm.disabledAnother = true;
            		vm.getChilds(1, vm.alpha.id, padre);
            	}else if(padre == 'workproduct'){
            		vm.disabledAlpha = true;
            		vm.disabledAnother = true;
            		vm.getChilds(1, vm.workProduct.id, padre);
            	}else if(padre == 'another'){
            		vm.disabledAlpha = true;
            		vm.disabledWork = true;
            	}
    		}else if(tipo == 2){
    			if(padre == 'alphaR'){
            		vm.disabledWorkR = true;
            		vm.disabledAnotherR = true;
            		vm.getChilds(1, vm.alphaResult.id, padre);
            	}else if(padre == 'workproductR'){
            		vm.disabledAlphaR = true;
            		vm.disabledAnotherR = true;
            		vm.getChilds(1, vm.workproductResult.id, padre);
            	}else if(padre == 'anotherR'){
            		vm.disabledAlphaR = true;
            		vm.disabledWorkR = true;
            	}
    		}
    			
        }
        
        function getChilds(tipo, id, padre){
        	if(tipo == 1){
        		if(padre == 'alpha'){
            		vm.estados = $filter('filter')(vm.alphas, { id: id})[0].states;
            		vm.visibleAlpha = true;
        		}else if(padre == 'workproduct'){
        			vm.levels = $filter('filter')(vm.workproducts, { id: id})[0].levelsOfDetails;
        			vm.visibleWorkproduct = true;
        		}
        	}else if(tipo == 2){
        		if(padre == 'alphaR'){
            		vm.estadosResult = $filter('filter')(vm.alphas, { id: id})[0].states;
            		vm.visibleAlphaR = true;
        		}else if(padre == 'workproductR'){
        			vm.levelsResult = $filter('filter')(vm.workproducts, { id: id})[0].levelsOfDetails;
        			vm.visibleWorkproductR = true;
        		}
        	}
        }
        
        function addItem (tipo) {
        	if(tipo == 1){
        		if(vm.alpha != null && vm.alpha != undefined && vm.alpha != "" && 
        				vm.alphaState != null && vm.alphaState != undefined && vm.alphaState != ""){
        			
            		vm.entriesTable.push({description: vm.alpha.name +' / '+ vm.alphaState.name});
            		
        		}else if(vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
        				vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != ""){

            		vm.entriesTable.push({description: vm.workProduct.name +' / '+ vm.levelOfDetail.name});
            		
        		}else if(vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
        				vm.anotherEntryCriteria != ""){

            		vm.entriesTable.push({description: vm.anotherEntryCriteria});
        		}
        		
        		vm.combos(1);
        		
        	}else if(tipo == 2){
        		if(vm.alphaResult != null && vm.alphaResult != undefined && vm.alphaResult != "" &&
        				vm.stateResult != null && vm.stateResult != undefined && vm.stateResult != ""){
        			
            		vm.resultsTable.push({description: vm.alphaResult.name +' / '+ vm.alphaStateResult.name});
            		
        		}else if(vm.workproPuctResult != null && vm.workProductResult != undefined && vm.workProductResult != "" &&
        				vm.levelOfDetailResult != null && vm.levelOfDetailResult != undefined && vm.levelOfDetailResult != ""){

            		vm.resultsTable.push({description: vm.workproPuctResult.name +' / '+ vm.levelOfDetailResult.name});
            		
        		}else if(vm.anotherEntryCriteriaResult != null && vm.anotherEntryCriteriaResult != undefined &&
        				vm.anotherEntryCriteriaResult != ""){

            		vm.resultsTable.push({description: vm.anotherEntryCriteriaResult});
        		}
        		
        		vm.combos(2);
        	}
        	
    	
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
