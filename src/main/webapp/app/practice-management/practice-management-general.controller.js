(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementGeneralController',PracticeManagementGeneralController);

    PracticeManagementGeneralController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', 'PracticeCatalogs', '$location'];

    function PracticeManagementGeneralController ($stateParams, JhiLanguageService, localStorageService, PracticeCatalogs, $location) {
        var vm = this;

        vm.load = load;
        vm.clean = clean;
        vm.practice = null;
        vm.indexKeyword = -1;
        vm.keywords = [];
        
        vm.deleteKeyword = deleteKeyword;
        vm.clearKeyword = clearKeyword;
        vm.addKeyword = addKeyword;
        vm.save = save;
        
        vm.load();
        
        
        function load () {
        	PracticeCatalogs.query(onSuccess, onError);
        }

        function onSuccess(data, headers) {
            vm.practice = localStorageService.get('practiceInEdition');
            console.log(vm.practice);
            if (angular.isUndefined(vm.practice) || vm.practice === null) {
                vm.practice = {};
            }
            if (!(angular.isUndefined(vm.practice.keywords) || vm.practice.keywords === null))
            {
                vm.keywords = vm.practice.keywords;
            }
            

        	localStorageService.set('actionsKinds', data.catalogs.actionsKinds);
        	localStorageService.set('kernels', data.catalogs.kernels);
        	localStorageService.set('activitySpaces', data.catalogs.activitySpaces);
        	localStorageService.set('alphas', data.catalogs.alphas);
        	localStorageService.set('workproducts', data.catalogs.workproducts);
        	localStorageService.set('competencies', data.catalogs.competencies);
        	localStorageService.set('practices', data.catalogs.practices);
        	localStorageService.set('resourcesTypes', data.catalogs.resourcesTypes);
        	
        	vm.kernels = localStorageService.get('kernels');
        	vm.practicesRelated = localStorageService.get('practices');
        	
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function addKeyword() {
    		
            if (!vm.newKeyword)
                return;
                
            if(vm.indexKeyword == -1)
            {
            	vm.keywords.push({keyWord: vm.newKeyword});
            }else{
            	vm.keywords[vm.indexKeyword].keyWord = vm.newKeyword;
            }
            
            console.log(vm.keywords);
            vm.clearKeyword();
    	}
        
        function deleteKeyword (index) {
            vm.keywords.splice(index, 1);
        }
    	
    	function clearKeyword () {
             vm.newKeyword = '';
             vm.indexKeyword = -1;
        }
    	
        function save() {
            vm.practice.keywords = vm.keywords;
            console.log(vm.practice);
            localStorageService.set('practiceInEdition', vm.practice);
            console.log($location.path());
	        $location.path('/practice-management/practiceConditions/');
        }

        function clean() {
            vm.practice = {};
            localStorageService.set('practiceInEdition', null);
        }
    	
    	vm.tinymceOptions = {
    	        resize: false,
    	        height: 100,
    	        plugins: 'textcolor',
    	        toolbar: "undo redo styleselect bold italic forecolor backcolor"

    	};
	}
        
    
})();
