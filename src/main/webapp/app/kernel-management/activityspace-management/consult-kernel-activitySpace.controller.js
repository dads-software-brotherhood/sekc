(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelActivitySpaceController', ConsultKernelActivitySpaceController);

    ConsultKernelActivitySpaceController.$inject = ['$stateParams', 'PracticeCatalogs', 'AlertService', 'localStorageService'];

    function ConsultKernelActivitySpaceController ($stateParams, PracticeCatalogs, AlertService, localStorageService) {
        var vm = this;
        
        vm.practice = null;
        vm.load = load;
        vm.fillActivitySpace = fillActivitySpace;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('activitySpaces')) || 
        			localStorageService.get('activitySpaces') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
            	vm.activitySpaces = localStorageService.get('activitySpaces');
        	}
        }

        function onSuccess(data, headers) {
        	localStorageService.set('activitySpaces', data.catalogs.activitySpaces);
        	vm.activitySpaces = localStorageService.get('activitySpaces');   	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function fillActivitySpace(activitySpace){
        	vm.activitySpaceActual = activitySpace;
        }
    }
})();
