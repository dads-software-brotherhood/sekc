(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelAlphasController', ConsultKernelAlphasController);

    ConsultKernelAlphasController.$inject = ['$stateParams', 'PracticeCatalogs', 'AlertService', 'localStorageService'];

    function ConsultKernelAlphasController ($stateParams, PracticeCatalogs, AlertService, localStorageService) {
        var vm = this;
        
        vm.practice = null;
        vm.load = load;
        vm.catalogs = catalogs;
        vm.fillAlpha = fillAlpha;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('activitySpaces')) || 
        			localStorageService.get('activitySpaces') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
        		vm.catalogs();
        	}
        }

        function onSuccess(data, headers) {
        	
        	localStorageService.set('activitySpaces', data.catalogs.activitySpaces);
        	localStorageService.set('alphas', data.catalogs.alphas);
        	localStorageService.set('workproducts', data.catalogs.workproducts);
        	localStorageService.set('competencies', data.catalogs.competencies);
        	
        	vm.catalogs();
        	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function catalogs() {
        	vm.activitySpaces = localStorageService.get('activitySpaces');
        	vm.alphas = localStorageService.get('alphas');
        	vm.workproducts = localStorageService.get('workproducts');
        	vm.competencies = localStorageService.get('competencies');
        }
        
        function fillAlpha(alpha){
        	vm.alphaActual = alpha;
        }
    }
})();
