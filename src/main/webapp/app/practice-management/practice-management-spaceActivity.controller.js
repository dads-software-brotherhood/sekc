(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementSpaceActivityController',PracticeManagementSpaceActivityController);

    PracticeManagementSpaceActivityController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'Practice', 'JhiLanguageService'];

    function PracticeManagementSpaceActivityController ($stateParams, $uibModalInstance, entity, Practice, JhiLanguageService) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;
        vm.addItem = addItem;
        vm.removeItem = removeItem;
        vm.practice = entity;
        vm.practice.rulesList = [];
        vm.practice.measuresList = [];
        vm.practice.entryList = [];
        vm.practice.resultList = [];

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.practice.id !== null) {
                Practice.update(vm.practice, onSaveSuccess, onSaveError);
            } else {
                Practice.save(vm.practice, onSaveSuccess, onSaveError);
            }
        }
        
        function addItem (list, item) {
        	var input = $("#" + item); 
        	if(input != null 
        			&& input.val().trim() != ""){
        		list.push(input.val().trim());
        		input.val("");
        	}
        }
        
        function removeItem (list, item) {
        	var index = list.indexOf(item);
            list.splice(index, 1);            
        }
        
    }
})();
