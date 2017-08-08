(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeConsultGeneralController', PracticeConsultGeneralController);

    PracticeConsultGeneralController.$inject = ['$stateParams', 'FindPractice'];

    function PracticeConsultGeneralController ($stateParams, FindPractice) {
        var vm = this;

        vm.load = load;
        vm.practice = {};

        vm.load($stateParams.idPractice);
        
        function load (id) {
        	FindPractice.get({id: id}, function(result) {
                vm.practice = result;
            });
        }
    }
})();
