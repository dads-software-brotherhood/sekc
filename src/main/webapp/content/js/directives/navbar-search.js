app.directive('navbarSearch', ['$timeout', function($timeout) {
  return {
    restrict: 'A',
    templateUrl: 'content/tpl/directives/navbar-search.html',
    link: function(scope, element, attrs) {
      scope.showNavbarSearch = false;

      scope.toggleSearch = function(){
        scope.showNavbarSearch = !scope.showNavbarSearch;
      };

      scope.submitNavbarSearch = function(){
        scope.showNavbarSearch = false;
      };
    }
  };
}]);
