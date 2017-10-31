(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeConsultGeneralController', PracticeConsultGeneralController);

    PracticeConsultGeneralController.$inject = ['$stateParams', 'FindPractice', '$location', 'JhiLanguageService','DataUtils'];

    function PracticeConsultGeneralController($stateParams, FindPractice, $location, JhiLanguageService, DataUtils) {
        var vm = this;

        vm.load = load;
        vm.practice = {};
        vm.id = $stateParams.idPractice;
        vm.downloadFile = DataUtils.downloadFile;
        
        vm.load();
        
        function load () {
        	if(vm.id){
	        	FindPractice.get({id: vm.id}, function(result) {
	                vm.practice = result;
                    console.log(vm.practice.thingsToDo);
	            });
        	}else{
        		$location.path('/find-practice');
        	}
        }
    }
})();
