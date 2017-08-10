(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeConsultGeneralController', PracticeConsultGeneralController);

    PracticeConsultGeneralController.$inject = ['$stateParams', 'FindPractice', '$location'];

    function PracticeConsultGeneralController ($stateParams, FindPractice, $location) {
        var vm = this;

        vm.load = load;
        vm.practice = {};
        vm.id = $stateParams.idPractice;
        
        vm.load();
        
        function load () {
        	if(vm.id != undefined && vm.id != null && vm.id != ""){
	        	FindPractice.get({id: vm.id}, function(result) {
	                vm.practice = result;
	            });
        	}else{
        		$location.path('/find-practice');
        	}
        }
    }
})();
