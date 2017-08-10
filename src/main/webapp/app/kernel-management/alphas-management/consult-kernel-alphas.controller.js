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
        vm.fillAlpha = fillAlpha;
        vm.fillState =fillState;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('alphas')) || 
        			localStorageService.get('alphas') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
            	vm.alphas = localStorageService.get('alphas');
        	}
        }

        function onSuccess(data, headers) {
        	localStorageService.set('alphas', data.catalogs.alphas);
        	vm.alphas = localStorageService.get('alphas');   	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function fillAlpha(alpha){
        	vm.alphaActual = alpha;
        }
        
        function fillState(state){
        	vm.stateActual = state;
        }
    }
})();
