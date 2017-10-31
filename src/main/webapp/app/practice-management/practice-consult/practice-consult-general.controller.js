(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeConsultGeneralController', PracticeConsultGeneralController);

    PracticeConsultGeneralController.$inject = ['$stateParams', 'FindPractice', '$location', 'JhiLanguageService'];

    function PracticeConsultGeneralController ($stateParams, FindPractice, $location, JhiLanguageService) {
        var vm = this;

        vm.load = load;
        vm.practice = {};
        vm.id = $stateParams.idPractice;
        
        vm.load();
        
        function load () {
        	if(vm.id){
	        	FindPractice.get({id: vm.id}, function(result) {
	                vm.practice = result;
	            });
        	}else{
        		$location.path('/find-practice');
        	}
        }
    }
})();
