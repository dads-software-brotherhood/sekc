angular
        .module('sekcApp').controller('TodoController', ['$scope', 'todoService', function($scope, todoService){
  $scope.todoService = new todoService($scope);
}]);
