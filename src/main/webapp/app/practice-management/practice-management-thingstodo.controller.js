(function () {
    'use strict';

    angular
        .module('sekcApp')
        .controller('PracticeManagementThingsToDoController', PracticeManagementThingsToDoController);

    PracticeManagementThingsToDoController.$inject = ['$stateParams', 'JhiLanguageService', 'localStorageService', '$filter', '$location', 'Practice', '$window', 'DataUtils'];

    function PracticeManagementThingsToDoController($stateParams, JhiLanguageService, localStorageService, $filter, $location, Practice, $window, DataUtils) {
        var vm = this;
        vm.error = false;
        vm.load = load;
        vm.practice = null;

        vm.addActivity = addActivity;
        vm.activityInEdition = null;
        vm.deleteActivity = deleteActivity;
        vm.editActivity = editActivity;
        vm.compositionActivities = compositionActivities;
        vm.addCompositionActivities = addCompositionActivities;
        vm.cleanCompositionActivities = cleanCompositionActivities;
        vm.cleanModales = cleanModales;
        vm.cleanModales();

        vm.addCompetency = addCompetency;
        vm.deleteCompetency = deleteCompetency;

        vm.addApproach = addApproach;
        vm.deleteApproach = deleteApproach;

        vm.addEntry = addEntry;
        vm.deleteEntry = deleteEntry;
        vm.alphaEntryFilter = alphaEntryFilter;
        vm.workProductsEntryFilter = workProductsEntryFilter;

        vm.addCompletition = addCompletition;
        vm.deleteCompletition = deleteCompletition;
        vm.alphaCompletitionFilter = alphaCompletitionFilter;
        vm.workProductsCompletitionFilter = workProductsCompletitionFilter;

        vm.actionInEdition = newAction();
        vm.addAction = addAction;
        vm.deleteAction = deleteAction;
        vm.deleteActionElement = deleteActionElement;
        vm.newAction = newAction;
        vm.fillAction = fillAction;
        vm.alphaStateFilter = alphaStateFilter;
        vm.workProductsFilter = workProductsFilter;
        vm.competenciesFilter = competenciesFilter;

        vm.addResource = addResource;
        vm.deleteResource = deleteResource;
        vm.byteSize = DataUtils.byteSize;
        vm.downloadFile = DataUtils.downloadFile;

        vm.save = save;
        vm.clean = clean;
        vm.validate = validate;

        vm.load();

        function load() {
            vm.practice = localStorageService.get('practiceInEdition');
            if (vm.practice == null) {
                vm.practice = {};
            }
            vm.areasOfConcern = localStorageService.get('areasOfConcern');
            vm.competencies = localStorageService.get('competencies');

            vm.actionsKinds = localStorageService.get('actionsKinds');
            vm.workProducts = localStorageService.get('workproducts');
            vm.alphas = localStorageService.get('alphas');
            vm.resourcesTypes = localStorageService.get('resourcesTypes');

            vm.activityInEdition = newActivity();
            if (angular.isUndefined(vm.practice.thingsToDo) || vm.practice.thingsToDo === null) {
                vm.practice.thingsToDo = { activities: [] };
            }

        }
        function onError(error) {
            //AlertService.error(error.data.message);
        }

        //---------------- Activity -----------------

        function addActivity() {
            if (vm.validate()) {
                vm.activityInEdition.areaOfConcern = vm.areaOfConcern;
                vm.activityInEdition.idAreaOfConcern = vm.areaOfConcern.id;
                vm.activityInEdition.activitySpace = vm.activitySpace;
                vm.activityInEdition.idActivitySpace = vm.activitySpace.id;
                vm.activityInEdition.nameActivitySpace = vm.activitySpace.name;
                vm.activityInEdition.goJsPosition = "";
                if (vm.activityInEdition.idActivityComposition == null ||
                    vm.activityInEdition.idActivityComposition == undefined) {
                    vm.activityInEdition.idActivityComposition = 0;
                    vm.activityInEdition.created = false;
                    vm.activityInEdition.idActivityComposition = vm.practice.thingsToDo.activities.length != 0 ?
                        Math.max.apply(
                            Math, vm.practice.thingsToDo.activities.map(
                                function (o) { return o.idActivityComposition; })) + 1 : 0;
                    console.log(vm.activityInEdition.idActivityComposition);
                    vm.practice.thingsToDo.activities.push(vm.activityInEdition);

                } else {
                    var editedActivity = $filter('filter')(vm.practice.thingsToDo.activities,
                        { idActivityComposition: vm.activityInEdition.idActivityComposition })[0];
                    editedActivity = vm.activityInEdition;
                }
                vm.clean();
            } else {
                vm.error = true;
                vm.mensaje = "practiceManagement.msg.1";
            }
            $window.scrollTo(0, 0);
            $('.collapse').collapse("hide");
        }
        function newActivity() {
            return {
                competencies: [],
                approaches: [],
                actions: [],
                resources: [],
                entryCriterion: {
                    alphaStates: [],
                    workProductsLevelofDetail: [],
                    otherConditions: []
                },
                completitionCriterion: {
                    alphaStates: [],
                    workProductsLevelofDetail: [],
                    otherConditions: []
                },
                to: []
            };
        }
        function deleteActivity(index) {
            vm.practice.thingsToDo.activities.splice(index, 1);
        }
        function editActivity(index) {
            vm.activityInEdition = vm.practice.thingsToDo.activities[index];
            vm.areaOfConcern = vm.practice.thingsToDo.activities[index].areaOfConcern;
            vm.activitySpace = vm.practice.thingsToDo.activities[index].activitySpace;
        }
        function compositionActivities() {
            //actividades guardadas en thingsToDo
            vm.activitiesDiagram = [];
            vm.activitiesRelation = [];
            //Se recorren las actividades para pintarlas en el diagrama
            angular.forEach(vm.practice.thingsToDo.activities, function (value, key) {
                var activity = {};
                activity.key = value.idActivityComposition;
                activity.name = value.name;
                activity.color = "#1E88E5";
                activity.idActivitySpace = vm.practice.thingsToDo.activities[key].idActivitySpace;
                activity.to = value.to;
                activity.loc = value.goJsPosition;
                vm.activitiesDiagram.push(activity);
                //Se recorren relaciones para agregarlas al diagrama
                angular.forEach(activity.to, function (value, key) {
                    vm.activitiesRelation.push({ from: activity.key, to: value });
                });
            });

            vm.model = new go.GraphLinksModel(
                vm.activitiesDiagram,
                vm.activitiesRelation);
            vm.model.selectedNodeData = null;
        }
        function addCompositionActivities() {

            //Se verifica que no hayan agregado más actividades de las que se pintan
            if (vm.model.nodeDataArray.length == vm.practice.thingsToDo.activities.length) {
                //Se recorren los nodos de las actividades para insertar su posición
                angular.forEach(vm.model.De, function (value, key) {
                    //Se busca la actividad para asignar su relación en el objeto thingsTo Do
                    var findActivityGoJS = $filter('filter')(vm.practice.thingsToDo.activities,
                        { idActivityComposition: value.key })[0];
                    findActivityGoJS.goJsPosition = vm.model.De[key].loc;
                    findActivityGoJS.to = [];
                });

                //Se recorren las relaciones de las actividades
                angular.forEach(vm.model.ff, function (value, key) {
                    //Se busca la actividad para asignar su relación en el objeto thingsTo Do
                    var findActivity = $filter('filter')(vm.practice.thingsToDo.activities,
                        { idActivityComposition: value.from })[0];
                    findActivity.to.push(vm.model.ff[key].to);
                });

                $('#compositionActivitiesDialog').modal('toggle');
            } else return;

        }

        //---------------- Competency -----------------
        function addCompetency() {
            console.log(vm.activityInEdition);
            if (vm.competency && vm.competencyLevel) {
                vm.activityInEdition.competencies.push({
                    idCompetency: vm.competency.id,
                    competency: vm.competency.name,
                    idCompetencyLevel: vm.competencyLevel.id,
                    competencyLevel: vm.competencyLevel.level + ' / ' + vm.competencyLevel.name
                });
                vm.cleanModales();
                $('#competencyDialog').modal('toggle');
            }
            console.log(vm.activityInEdition);
        }
        function deleteCompetency(index) {
            vm.activityInEdition.competencies.splice(index, 1);
        }
        function competenciesFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.competencies, {
                idCompetency: vm.competency.id,
                idCompetencyLevel: alphaState.id
            }).length == 0;
        }

        //---------------- Approach -----------------

        function addApproach() {
            if (vm.nameApproach && vm.descriptionApproach) {

                vm.activityInEdition.approaches.push({
                    name: vm.nameApproach,
                    description: vm.descriptionApproach
                });
                vm.cleanModales();
                $('#approachDialog').modal('toggle');
            }
        }
        function deleteApproach(index) {
            vm.activityInEdition.approaches.splice(index, 1);
        }

        //---------------- Action -----------------

        function newAction() {
            return {
                idActionKind: null,
                alphaStates: [],
                workProductsLevelofDetail: []
            };
        }
        function fillAction() {
            if (vm.actionInEdition.idActionKind && vm.alpha && vm.alphaState) {
                if (vm.actionInEdition.workProductsLevelofDetail.length) {
                    return;
                } else {
                    vm.actionInEdition.alphaStates.push(
                        {
                            description: vm.alpha.name + ' - ' + vm.alphaState.name,
                            idAlpha: vm.alpha.id, idState: vm.alphaState.id
                        }
                    );
                    vm.alpha = null;
                }
                vm.cleanModales();
            } else if (vm.actionInEdition.idActionKind && vm.workProduct && vm.levelOfDetail) {
                if (vm.actionInEdition.alphaStates.length) {
                    return;
                } else {
                    vm.actionInEdition.workProductsLevelofDetail.push(
                        {
                        description: vm.workProduct.name + ' - ' + vm.levelOfDetail.name,
                        idWorkProduct: vm.workProduct.id,
                        idLevelOfDetail: vm.levelOfDetail.id
                        }
                    );
                    vm.workProduct = null;
                }
            }
        }
        function deleteActionElement(index, tipo) {
            if (tipo === "alpha") {
                vm.actionInEdition.alphaStates.splice(index, 1);
            }
            else if (tipo === "workproduct") {
                vm.actionInEdition.workProductsLevelofDetail.splice(index, 1);
            }
        }

        function addAction() {
            if (vm.actionInEdition.idActionKind &&
                (vm.actionInEdition.alphaStates.length ||
                    vm.actionInEdition.workProductsLevelofDetail.length)
            ) {
                vm.activityInEdition.actions.push(vm.actionInEdition);
                vm.actionInEdition = newAction();
                vm.cleanModales();
                $('#actionDialog').modal('toggle');
            }
        }
        function deleteAction(index) {
            vm.activityInEdition.actions.splice(index, 1);
        }
        function alphaStateFilter(alphaState) {
            return $filter('filter')(vm.actionInEdition.alphaStates, {
                idAlpha: vm.alpha.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsFilter(levelOfDetails) {
            return $filter('filter')(vm.actionInEdition.workProductsLevelofDetail, {
                idWorkProduct: vm.workProduct.id,
                idLevelOfDetail: levelOfDetails.id
            }).length == 0;
        }

        //---------------- Entry -----------------

        function addEntry() {
            if (vm.alpha && vm.alphaState) {
                vm.activityInEdition.entryCriterion.alphaStates.push({
                    description: vm.alpha.name + ' / ' + vm.alphaState.name,
                    idAlpha: vm.alpha.id,
                    idState: vm.alphaState.id
                });
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            } else if (vm.workProduct && vm.levelOfDetail) {
                vm.activityInEdition.entryCriterion.workProductsLevelofDetail.push({
                    description: vm.workProduct.name + ' / ' + vm.levelOfDetail.name,
                    idWorkProduct: vm.workProduct.id,
                    idLevelOfDetail: vm.levelOfDetail.id
                });
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            } else if (vm.anotherEntryCriteria) {
                vm.activityInEdition.entryCriterion.otherConditions.push(
                    vm.anotherEntryCriteria
                );
                vm.cleanModales();
                $('#entryDialog').modal('toggle');
            }
        }
        function deleteEntry(index, tipo) {
            if (tipo === 'alpha') {
                vm.activityInEdition.entryCriterion.alphaStates.splice(index, 1);
            } else if (tipo === 'workproduct') {
                vm.activityInEdition.entryCriterion.workProductsLevelofDetail.splice(index, 1);
            } else if (tipo === 'other') {
                vm.activityInEdition.entryCriterion.otherConditions.splice(index, 1);
            }
        }
        function alphaEntryFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.entryCriterion.alphaStates, {
                idAlpha: vm.alpha.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsEntryFilter(levelOfDetails) {
            return $filter('filter')(vm.activityInEdition.entryCriterion.workProductsLevelofDetail, {
                idWorkProduct: vm.workProduct.id,
                idLevelOfDetail: levelOfDetails.id
            }).length == 0;
        }
        //---------------- Completition -----------------

        function addCompletition() {
            if (vm.alphaCompletition && vm.alphaStateCompletition) {
                vm.activityInEdition.completitionCriterion.alphaStates.push({
                    description: vm.alphaCompletition.name + ' / ' + vm.alphaStateCompletition.name,
                    idAlpha: vm.alphaCompletition.id,
                    idState: vm.alphaStateCompletition.id
                });
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            } else if (vm.workProductCompletition && vm.levelOfDetailCompletition) {

                vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.push({
                    description: vm.workProductCompletition.name + ' / ' + vm.levelOfDetailCompletition.name,
                    idWorkProduct: vm.workProductCompletition.id,
                    idLevelOfDetail: vm.levelOfDetailCompletition.id
                });
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            } else if (vm.anotherEntryCriteriaCompletition) {
                vm.activityInEdition.completitionCriterion.otherConditions.push(
                    vm.anotherEntryCriteriaCompletition
                );
                vm.cleanModales();
                $('#completitionDialog').modal('toggle');
            }
        }
        function deleteCompletition(index, tipo) {
            if (tipo === 'alpha') {
                vm.activityInEdition.completitionCriterion.alphaStates.splice(index, 1);
            } else if (tipo === 'workproduct') {
                vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.splice(index, 1);
            } else if (tipo === 'other') {
                vm.activityInEdition.completitionCriterion.otherConditions.splice(index, 1);
            }
        }
        function alphaCompletitionFilter(alphaState) {
            return $filter('filter')(vm.activityInEdition.completitionCriterion.alphaStates, {
                idAlpha: vm.alphaCompletition.id,
                idState: alphaState.id
            }).length == 0;
        }
        function workProductsCompletitionFilter(levelOfDetails) {
            return $filter('filter')(vm.activityInEdition.completitionCriterion.workProductsLevelofDetail, {
                idWorkProduct: vm.workProductCompletition.id,
                idLevelOfDetail: levelOfDetails.id
            }).length == 0;
        }

        //---------------- Resource -----------------

        function addResource() {
            if (vm.type && vm.descriptionResource) {
                var resource = {};

                resource.file = vm.type.id == "URL" ? vm.url : vm.attachment.base64;
                resource.fileName = vm.type.id != "URL" && vm.attachment != null ? vm.attachment.filename : vm.url;
                resource.idTypeResource = vm.type.id;
                resource.content = vm.descriptionResource;
                resource.fileType = vm.type.id != "URL" ? vm.attachment.filetype : "";

                vm.activityInEdition.resources.push(resource);

                vm.cleanModales();
                $('#resourceDialog').modal('toggle');
            }
        }
        function deleteResource(index) {
            vm.activityInEdition.resources.splice(index, 1);
        }

        function cleanCompositionActivities() {
            angular.forEach(vm.practice.thingsToDo.activities, function (value, key) {
                vm.practice.thingsToDo.activities[key].to = [];
            });
            vm.compositionActivities();
        }
        function cleanModales() {
            vm.nameApproach = null;
            vm.descriptionApproach = null;

            vm.alphaCompletition = null;
            vm.alphaStateCompletition = null;
            vm.workProductCompletition = null;
            vm.levelOfDetailCompletition = null;
            vm.descriptionCompletition = null;

            vm.type = null;
            vm.attachment = null;
            angular.element("input[type='file']").val(null);
            vm.descriptionResource = null;
            vm.url = null;

            vm.competency = null;
            vm.competencyLevel = null;

            vm.alpha = null;
            vm.alphaState = null;
            vm.workProduct = null;
            vm.levelOfDetail = null;
            vm.descriptionEntry = null;
            vm.anotherEntryCriteria = null;
            vm.anotherEntryCriteriaCompletition = null;
            
        }
        function clean() {
            vm.activitySpace = null;
            vm.areaOfConcern = null;
            vm.activityInEdition = newActivity();
        }
        function save() {
            if (vm.practice.thingsToDo.activities.length) {
                console.log(JSON.stringify(vm.practice, null, "\t"));
                localStorageService.set('practiceInEdition', vm.practice);
                if (vm.practice.id !== null) {
                    Practice.update(vm.practice, onSaveSuccess, onSaveError);
                } else {
                    Practice.save(vm.practice, onSaveSuccess, onSaveError);
                }
            } else {
                vm.error = true;
                vm.mensaje = "practiceManagement.msg.3";
                $window.scrollTo(0, 0);
            }

        }
        function onSaveSuccess(result) {
            vm.practice = {};
            localStorageService.set('practiceInEdition', null);
            $location.path('/find-practice');
        }
        function onSaveError() {
            vm.error = true;
            vm.mensaje = "practiceManagement.msg.6";
            $window.scrollTo(0, 0);
        }
        function validate() {
            if (!vm.areaOfConcern ||
                !vm.activitySpace ||
                !vm.activityInEdition.name ||
                !vm.activityInEdition.briefDescription ||
                !vm.activityInEdition.description ||
                !vm.activityInEdition.competencies.length ||
                !vm.activityInEdition.approaches.length ||
                !vm.activityInEdition.actions.length ||
                (!vm.activityInEdition.entryCriterion.alphaStates.length &&
                    !vm.activityInEdition.entryCriterion.workProductsLevelofDetail.length &&
                    !vm.activityInEdition.entryCriterion.otherConditions.length) ||
                (!vm.activityInEdition.completitionCriterion.alphaStates.length &&
                    !vm.activityInEdition.completitionCriterion.workProductsLevelofDetail.length &&
                    !vm.activityInEdition.completitionCriterion.otherConditions.length)
            ) {
                return false;
            }
            return true;
        }
    }


})();
