(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('FindPracticeDeleteController', FindPracticeDeleteController);

    FindPracticeDeleteController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'FindPractice', 'JhiLanguageService'];

    function FindPracticeDeleteController ($stateParams, $uibModalInstance, entity, FindPractice, JhiLanguageService) {
        var vm = this;

        vm.practice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
        	FindPractice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
