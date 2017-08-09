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
        vm.fillState =fillState;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('alphas')) || 
        			localStorageService.get('alphas') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
        		vm.catalogs();
        	}
        }

        function onSuccess(data, headers) {
        	
        	localStorageService.set('alphas', data.catalogs.alphas);
        	
        	vm.catalogs();
        	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function catalogs() {
        	vm.alphas = localStorageService.get('alphas');
        }
        
        function fillAlpha(alpha){
        	vm.alphaActual = alpha;
        }
        
        function fillState(state){
        	vm.stateActual = state;
        }
    }
})();
