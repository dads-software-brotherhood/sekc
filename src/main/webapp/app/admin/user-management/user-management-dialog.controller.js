(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'ProfileService', 'JhiLanguageService'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, ProfileService, JhiLanguageService) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN','ROLE_CONSULTANT','ROLE_VALIDATOR','ROLE_PRACTICING'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        //temporalmente se activa en lo que se define..
        vm.user.activated = true;

        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

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
            if(!vm.user.authorities){
            	vm.user.authorities = 'ROLE_USER';
            }
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
