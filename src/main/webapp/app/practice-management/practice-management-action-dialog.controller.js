(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementActionDialogController',PracticeManagementActionDialogController);

    PracticeManagementActionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService'];

    function PracticeManagementActionDialogController ($stateParams,  $uibModalInstance, JhiLanguageService, localStorageService) {
        var vm = this;
        
        vm.clean = clean;
        vm.load = load;
        vm.save = save;
        vm.cancel = cancel;

        vm.load();
        

        function load() {

            vm.actionsKinds = localStorageService.get('actionsKinds');
            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
            vm.practice = localStorageService.get('practiceInEdition');
        	
        	if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
	        	
		        vm.practice.thingsToDo = { competencies : [], approaches : [], actions : [], entryCriterion : [], completitionCriterion : [], resources : [] };
            }
        	if (angular.isUndefined(vm.practice.thingsToDo.actions.alphaStates) || vm.practice.thingsToDo.actions.alphaStates === null) {
    	        vm.practice.thingsToDo.actions = { alphaStates : [], workProductsLevelofDetail : [] };
        	}

        }
        
        function save () { 
        	
            if (vm.alpha != null && vm.alpha != undefined &&
                vm.alphaState != null && vm.alphaState != undefined) {

                vm.practice.thingsToDo.actions.alphaStates.push({ description: vm.alpha.name + ' / ' + vm.alphaState.name, idAlpha: vm.alpha.id, idState: vm.alphaState.id, idActionKind : vm.actionKind.id });
            
            } else if (vm.workProduct != null && vm.workProduct != undefined && vm.workProduct != "" &&
                vm.levelOfDetail != null && vm.levelOfDetail != undefined && vm.levelOfDetail != "") {

                vm.practice.thingsToDo.actions.workProductsLevelofDetail.push({ description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name, idWorkProduct: vm.workProduct.id, idLevelOfDetail: vm.levelOfDetail.id, idActionKind : vm.actionKind.id});

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
        	vm.practice.thingsToDo.actions.actionKind = null;
        }
        
        function cancel () {
        	
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
