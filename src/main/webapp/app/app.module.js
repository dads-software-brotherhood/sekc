(function() {
    'use strict';

    angular
        .module('sekcApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',

            // materialism
            'ngAnimate',
            'ngSanitize',
            'ngPlaceholders',
            'ngTable',

            'angular-loading-bar',

            'angulartics',
            'angulartics.google.analytics',

            'nemLogging',
            'uiGmapgoogle-maps',
            'ui.select',

            'gridshore.c3js.chart',
            'monospaced.elastic',     // resizable textarea
            'mgcrea.ngStrap',
            'jcs-autoValidate',
            'ngFileUpload',
            'textAngular',
            'fsm',                    // sticky header
            'smoothScroll',
            'LocalStorageModule',
            'ui.tinymce',
            'ivh.treeview',
            'file-model',
            'naif.base64'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
