(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementCompetencyDialogController',PracticeManagementCompetencyDialogController);

    PracticeManagementCompetencyDialogController.$inject = ['$stateParams', '$uibModalInstance', 'JhiLanguageService', 'localStorageService', '$rootScope'];

    function PracticeManagementCompetencyDialogController($stateParams, $uibModalInstance, JhiLanguageService, localStorageService, $rootScope) {
        var vm = this;
        
        vm.clean = clean;
        vm.load = load;
        vm.save = save;
        vm.cancel = cancel;

        vm.load();
        

        function load() {
            vm.competencies = localStorageService.get('competencies');
            console.log($rootScope.activityInEdition);
        }

        function save() {
            if (vm.competency != null && vm.competency != undefined &&
                vm.competencyLevel != null && vm.competencyLevel != undefined) {
                $rootScope.activityInEdition.competencies.push({ idCompetency: vm.competency.id, competency: vm.competency.name, idCompetencyLevel: vm.competencyLevel.id, competencyLevel: vm.competencyLevel.level + ' / ' + vm.competencyLevel.name });
                console.log($rootScope);
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
