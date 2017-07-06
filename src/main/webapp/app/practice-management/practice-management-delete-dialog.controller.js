(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementDeleteController', PracticeManagementDeleteController);

    PracticeManagementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Practice'];

    function PracticeManagementDeleteController ($uibModalInstance, entity, Practice) {
        var vm = this;

        vm.practice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (login) {
            Practice.delete({login: login},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
