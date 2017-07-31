(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompletitionDialogController',PracticeManagementCompletitionDialogController);

    PracticeManagementCompletitionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementCompletitionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clean = clean;
        vm.load = load;
        vm.save = save;
        vm.cancel = cancel;

        vm.load();
        

        function load() {
            console.log(Activity);
            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
            vm.practice = localStorageService.get('practiceInEdition');
            
	        console.log(vm.practice);
	        
	        
	        if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
            }
	        if(angular.isUndefined(vm.practice.thingsToDo.completitionCriterion.alphaStates) || vm.practice.thingsToDo.completitionCriterion.alphaStates === null){
		        vm.practice.thingsToDo.completitionCriterion = { alphaStates : [], workProductsLevelofDetail : [], otherConditions: [] }	
	        }

              
        }

        function save() {
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {

                vm.practice.thingsToDo.completitionCriterion.alphaStates.push({ description: vm.alpha.name + ' / ' + vm.alphaState.name, idAlpha: vm.alpha.id, idState: vm.alphaState.id, briefDescription : vm.description});
            
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

            	vm.practice.thingsToDo.completitionCriterion.workProductsLevelofDetail.push({ description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name, idWorkProduct: vm.workProduct.id, idLevelOfDetail: vm.levelOfDetail.id, briefDescription : vm.description});

            } else if (vm.anotherEntryCriteria != null && vm.anotherEntryCriteria != undefined &&
                vm.anotherEntryCriteria != "") {

            	vm.practice.thingsToDo.completitionCriterion.otherConditions.push({ description: vm.anotherEntryCriteria, briefDescription : vm.description });
            }
            
            localStorageService.set('practiceInEdition', vm.practice);
            vm.clean();
            $uibModalInstance.dismiss('cancel');

        }
        
        function clean () {
        	vm.alpha = null;
        	vm.alphaState = null;
        	vm.workProduct = null;
        	vm.levelOfDetail = null;
        	vm.description = null;
        }
        
        function cancel () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
