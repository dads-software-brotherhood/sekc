(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelWorkproductsController', ConsultKernelWorkproductsController);

    ConsultKernelWorkproductsController.$inject = ['$stateParams', 'PracticeCatalogs', 'AlertService', 'localStorageService'];

    function ConsultKernelWorkproductsController ($stateParams, PracticeCatalogs, AlertService, localStorageService) {
        var vm = this;
        
        vm.practice = null;
        vm.load = load;
        vm.fillWorkproduct = fillWorkproduct;
        vm.fillLevelsOfDetail =fillLevelsOfDetail;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('workproducts')) || 
        			localStorageService.get('workproducts') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
            	vm.workproducts = localStorageService.get('workproducts');
        	}
        }

        function onSuccess(data, headers) {
        	localStorageService.set('workproducts', data.catalogs.workproducts);
        	vm.workproducts = localStorageService.get('workproducts');	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function fillWorkproduct(workproduct){
        	vm.workproductActual = workproduct;
        }
        
        function fillLevelsOfDetail(levelOfDetail){
        	vm.levelOfDetailActual = levelOfDetail;
        }
    }
})();
