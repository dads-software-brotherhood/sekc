// bar-module
(function(){
    'use strict';
    angular.module('gridshore.c3js.bar', [
        //'graphApp.services',
        'ui.router'
    ]);
})();

(function(){
    'use strict';
    angular.module('gridshore.c3js.bar')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider'];
    function routeConfig($stateProvider) {
        $stateProvider
            .state('bar', {
                url: '/bar',
                templateUrl: 'assets/js/bar/bar.tpl.html',
                controller: 'BarCtrl',
                controllerAs: 'vm'
            });
    }
})();

(function(){
    'use strict';
    angular.module('gridshore.c3js.bar')
        .controller('BarCtrl', BarCtrl);

    BarCtrl.$inject = [];
    function BarCtrl() {
        var vm = this;
        vm.chartObj = {};
        vm.isGrouped = false;

        vm.handleCallbackGroup = handleCallbackGroup;
        vm.toggleGroup = toggleGroup;

        vm.datapoints = [
            {"x": "one", "top-1": 10, "top-2": 12},
            {"x": "two", "top-1": 11, "top-2": 13},
            {"x": "three", "top-1": 12, "top-2": 14},
            {"x": "four", "top-1": 13, "top-2": 15},
            {"x": "five", "top-1": 14, "top-2": 16}
        ];
        vm.datacolumns = [{"id": "top-1", "type": "bar", "name": "Top one"},
            {"id": "top-2", "type": "bar", "name": "Top two"}];
        vm.datax = {"id": "x"};

        function handleCallbackGroup(chartObj) {
            vm.chartObj = chartObj;
        }

        function toggleGroup() {
            if (vm.isGrouped) {
                vm.chartObj.groups([]);
            } else {
                vm.chartObj.groups([['bar1','bar2']]);
            }
            vm.isGrouped = !vm.isGrouped;
            vm.chartObj.flush();
        }
    }
})();