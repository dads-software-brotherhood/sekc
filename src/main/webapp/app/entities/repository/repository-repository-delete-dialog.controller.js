(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('RepositoryRepositoryDeleteController',RepositoryRepositoryDeleteController);

    RepositoryRepositoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Repository'];

    function RepositoryRepositoryDeleteController($uibModalInstance, entity, Repository) {
        var vm = this;

        vm.repository = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Repository.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
