(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('FindPracticeDetailController', FindPracticeDetailController);

    FindPracticeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Repository'];

    function FindPracticeDetailController($scope, $rootScope, $stateParams, previousState, entity, Repository) {
        var vm = this;

        vm.repository = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sekcApp:repositoryUpdate', function(event, result) {
            vm.repository = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
