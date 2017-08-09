(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultKernelCompetenciesController', ConsultKernelCompetenciesController);

    ConsultKernelCompetenciesController.$inject = ['$stateParams', 'PracticeCatalogs', 'AlertService', 'localStorageService'];

    function ConsultKernelCompetenciesController ($stateParams, PracticeCatalogs, AlertService, localStorageService) {
        var vm = this;
        
        vm.practice = null;
        vm.load = load;
        vm.fillCompetency = fillCompetency;
        vm.fillLevel =fillLevel;
        
        vm.load();
        
        
        function load () {
        	if(angular.isUndefined(localStorageService.get('competencies')) || 
        			localStorageService.get('competencies') == null){
            	PracticeCatalogs.query(onSuccess, onError);
        	}else{
        		vm.competencies = localStorageService.get('competencies');
        	}
        }

        function onSuccess(data, headers) {
        	localStorageService.set('competencies', data.catalogs.competencies);
        	vm.competencies = localStorageService.get('competencies');
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
                
        function fillCompetency(competency){
        	vm.competencyActual = competency;
        }
        
        function fillLevel(level){
        	vm.levelActual = level;
        }
    }
})();
