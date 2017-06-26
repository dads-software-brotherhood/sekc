(function() {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementGeneralController',PracticeManagementGeneralController);

    PracticeManagementGeneralController.$inject = ['$stateParams', 'JhiLanguageService'];

    function PracticeManagementGeneralController ($stateParams, JhiLanguageService) {
        var vm = this;

        vm.indexKeyword = -1;
        vm.buttonAdd = "Add";
        vm.keywords = [];
        
        vm.deleteKeyword = deleteKeyword;
        vm.updateKeyword = updateKeyword;
        vm.clearKeyword = clearKeyword;
        vm.addKeyword = addKeyword;
        
        // Delete keyword
        function deleteKeyword (index) {
                // Remove from main keywords (using index)
                vm.keywords.splice(index, 1);
        }
    
    
    	function updateKeyword (index, keyword) {    		
	        vm.newKeyword = keyword.keyWord;
	        vm.indexKeyword = index;
	        vm.buttonAdd = "Save";
	        
    	}
    	
    	function clearKeyword () {
             vm.newKeyword = '';
             vm.indexKeyword = -1;
             vm.buttonAdd = "Add";
        }
    	
    	function addKeyword() {
            // Do nothing if no newKeyword is entered (blank)
            if (!vm.newKeyword)
                return;
                
            if(vm.indexKeyword == -1)
            {
            	vm.keywords.push({keyWord: vm.newKeyword});
            }else{
            	vm.keywords[vm.indexKeyword].keyWord = vm.newKeyword;
            }
            
            vm.clearKeyword();
    	}
	}
        
    
})();
