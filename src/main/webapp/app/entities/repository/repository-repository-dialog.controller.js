(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('RepositoryRepositoryDialogController', RepositoryRepositoryDialogController);

    RepositoryRepositoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Repository'];

    function RepositoryRepositoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Repository) {
        var vm = this;

        vm.repository = entity;
        vm.clear = clear;
        vm.save = save;
        vm.regex = '^((https?|ftp)://)?([A-Za-z]+\\.)?[A-Za-z0-9-]+(\\.[a-zA-Z]{1,4}){1,2}(/.*\\?.*)?$';

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.repository.id !== null) {
                Repository.update(vm.repository, onSaveSuccess, onSaveError);
            } else {
                Repository.save(vm.repository, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sekcApp:repositoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
