(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementGeneralController',PracticeManagementGeneralController);

    PracticeManagementGeneralController.$inject = ['$stateParams', 'JhiLanguageService', 'entity', 'localStorageService', 'PracticeCatalogs', '$location'];

    function PracticeManagementGeneralController ($stateParams, JhiLanguageService, entity ,localStorageService, PracticeCatalogs, $location) {
        var vm = this;
        
        vm.load = load;
        vm.clean = clean;
        vm.practice = entity;
        vm.indexKeyword = -1;
        
        vm.deleteKeyword = deleteKeyword;
        vm.clearKeyword = clearKeyword;
        vm.addKeyword = addKeyword;
        vm.save = save;
        
        vm.load();
        
        
        function load () {
        	PracticeCatalogs.query(onSuccess, onError);
        }

        function onSuccess(data, headers) {
        	if (vm.practice.id == null) {
	            vm.practice = localStorageService.get('practiceInEdition');
	            if (angular.isUndefined(vm.practice) || vm.practice === null) {
		              vm.practice = entity;
	            }
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
        	vm.relatedPractices = localStorageService.get('practices');
        }
        
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function addKeyword() {
    		
            if (!vm.newKeyword)
                return;
                
            if(vm.indexKeyword == -1)
            {
            	vm.practice.keyWords.push(vm.newKeyword);
            }else{
            	vm.practice.keyWords[vm.indexKeyword].keyWord = vm.newKeyword;
            }
            
            console.log(vm.practice.keyWords);
            vm.clearKeyword();
    	}
        
        function deleteKeyword (index) {
        	vm.practice.keyWords.splice(index, 1);
        }
    	
    	function clearKeyword () {
             vm.newKeyword = '';
             vm.indexKeyword = -1;
        }
    	
        function save() {
            localStorageService.set('practiceInEdition', vm.practice);
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
