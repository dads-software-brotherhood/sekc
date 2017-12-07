(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultGlossaryController', ConsultGlossaryController);

    ConsultGlossaryController.$inject = ['$stateParams', 'GlossaryService', 'AlertService'];

    function ConsultGlossaryController($stateParams, GlossaryService, AlertService) {
        var vm = this;
        vm.letraSeleccionada = "";

        vm.abc = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];

        vm.load = load;
        vm.load();


        function load() {
            GlossaryService.getGlossary().then(function (response) {
                vm.glossary = response.data;
            });
        }
    }
})();
