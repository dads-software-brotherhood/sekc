(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('FindPracticeDetailController', FindPracticeDetailController);

    FindPracticeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Repository'];

    function FindPracticeDetailController($scope, $rootScope, $stateParams, previousState, entity, Repository) {
        var vm = this;

        vm.practice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sekcApp:findPracticeUpdate', function(event, result) {
            vm.repository = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
