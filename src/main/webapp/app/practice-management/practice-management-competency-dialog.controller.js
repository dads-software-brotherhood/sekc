(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompetencyDialogController',PracticeManagementCompetencyDialogController);

    PracticeManagementCompetencyDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementCompetencyDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clean = clean;
        vm.load = load;
        vm.save = save;
        vm.cancel = cancel;

        vm.load();
        

        function load() {

            vm.competencies = localStorageService.get('competencies');
            vm.practice = localStorageService.get('practiceInEdition');
            
	        console.log(vm.practice);
	        
	        
	        if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	

		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
                
            }
              
        }

        function save() {
            if (vm.competency != null && vm.competency != undefined &&
            		vm.competencyLevel != null && vm.competencyLevel != undefined) {

                vm.practice.thingsToDo.competencies.push({ idCompetency: vm.competency.id, competency: vm.competency.name, idCompetencyLevel: vm.competencyLevel.id, competencyLevel: vm.competencyLevel.level + ' / ' + vm.competencyLevel.name});
            
	            localStorageService.set('practiceInEdition', vm.practice);
	            vm.clean();
	            $uibModalInstance.dismiss('cancel');
            }

        }
        
        function clean () {
        	vm.competency = null;
        	vm.competencyLevel = null;
        }
        
        function cancel () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
