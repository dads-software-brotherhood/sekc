(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('ConsultGlossaryController', ConsultGlossaryController);

    ConsultGlossaryController.$inject = ['$stateParams', 'GlossaryService', 'AlertService'];

    function ConsultGlossaryController($stateParams, GlossaryService, AlertService) {
        var vm = this;
        vm.letraSeleccionada = "";

        vm.abc = [
            { "id": "A" }, { "id": "B" }, { "id": "C" }, { "id": "D" },
            { "id": "E" }, { "id": "F" }, { "id": "G" }, { "id": "H" },
            { "id": "I" }, { "id": "J" }, { "id": "K" }, { "id": "L" },
            { "id": "M" }, { "id": "N" }, { "id": "O" }, { "id": "P" },
            { "id": "Q" }, { "id": "R" }, { "id": "S" }, { "id": "T" },
            { "id": "U" }, { "id": "V" }, { "id": "W" }, { "id": "X" },
            { "id": "Y" }, { "id": "Z" }
        ];
        vm.load = load;

        vm.load();


        function load() {
            GlossaryService.getGlossary().then(function (response) {
                vm.glossary = response.data;
            });
        }
    }
})();
