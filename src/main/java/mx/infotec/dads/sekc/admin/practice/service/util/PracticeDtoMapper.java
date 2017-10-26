package mx.infotec.dads.sekc.admin.practice.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEAction;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivity;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivityAssociation;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEApproach;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECompletionCriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEEntryCriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SEKernel;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.SEResource;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.essence.util.ResourcesTypes;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.practice.dto.Activity;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Criteriable;
import mx.infotec.dads.sekc.admin.practice.dto.Entries;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.Results;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;
import static mx.infotec.dads.sekc.admin.practice.service.util.SEEssenceMapper.repoUtil;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateConditions;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateGeneralInformation;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateWorkProductLevelOfDetailCriterion;
import mx.infotec.dads.sekc.exception.SekcException;
import mx.infotec.dads.sekc.util.EntityBuilder;
import mx.infotec.dads.sekc.util.EssenceFilter;
import org.omg.essence.model.activityspaceandactivity.ActionKind;

public class PracticeDtoMapper {

    private PracticeDtoMapper() {

    }

    /**
     * Mapper for convert Repository entity to PracticeDto
     * 
     * @param entity
     * @return PracticeDto
     */
    public static PracticeDto toDto(SEPractice entity) {
        PracticeDto dto = new PracticeDto();
        mapGeneralInfo(entity, dto);
        mapRelatedPractices(entity, dto);
        mapConditions(entity, dto);
        return dto;
    }
    
    public static SEPractice mapSEPractice(PracticeDto from, RandomRepositoryUtil repoUtils) {
        repoUtil = repoUtils;
        validateGeneralInformation(from);
        SEPractice to = EntityBuilder.build(practice -> {
            
            mapThingsToWorkWith(from.getThingsToWorkWith(), practice);
//            mapThingsToDo(from.getThingsToDo(), practice);
        }, SEPractice.class);
        return to;
    }

    public static void mapGeneralInfo(SEPractice entity, PracticeDto dto ) {
        dto.setId(entity.getId());
        dto.setIdKernel( entity.getOwner().getId());
        dto.setName(entity.getName());
        dto.setObjective(entity.getObjective());
        dto.setBriefDesciption(entity.getBriefDescription());
        dto.setDescription(entity.getDescription());
        dto.setConsistencyRules(entity.getConsistencyRules());
        dto.setAuthor(entity.getAuthor());
        dto.setKeywords(entity.getKeyWords());
    }

    public static void mapConditions(SEPractice entity, PracticeDto dto ){//Conditions conditions, SEPractice to) {
        mapCriterios( entity.getEntryCriterion(), dto, true);
        mapCriterios( entity.getResultCriterion(), dto, false);
    }

    private static void mapCriterios(Collection<SECriterion> seCriterionList, PracticeDto dto, boolean isEntry) {
        dto.setConditions( new Conditions());
        mapAlphaCriterion(seCriterionList,  dto, isEntry);
       // mapWorkProductCriterion(seCriterionList,  dto, isEntry); # TODO
      //  mapOtherCriterion(seCriterionList,  dto, isEntry);
    }

    /*
    private static void mapOtherCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
        Optional.of(criteriable.getOtherConditions()).ifPresent(entry -> {
            if (isEntry) {
                mapResultOtherEntryConditions(entry, seCriterionList);
            } else {
                mapResultOtherResultConditions(entry, seCriterionList);
            }
        });
    }*/ //# TODO
/*
    private static void mapWorkProductCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
        Optional.of(criteriable.getWorkProductsLevelofDetail()).ifPresent(workProducts -> {
            if (isEntry) {
                workProducts.forEach(entry -> mapResultWorkProductLevelOfDetailEntryCriterion(entry, seCriterionList));
            } else {
                workProducts.forEach(entry -> mapResultWorkProductLevelOfDetailResultCriterion(entry, seCriterionList));
            }
        });
    }*/

    private static void mapAlphaCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
            
            if (isEntry) {
                dto.getConditions().setEntries(new Entries());
                dto.getConditions().getEntries().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesEntryCriterion(entry, dto));
            } else {
                dto.getConditions().setResults(new Results());
                dto.getConditions().getResults().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesResultCriterion(entry, dto));
            }
        
    }

    private static void mapResultAlphaStatesEntryCriterion(SECriterion criterion, PracticeDto dto) {
        if (criterion.getState() != null){    
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getConditions().getEntries().getAlphaStates().add(alphaState);
        }
    }

    /**
     * Map AlphaStates to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultAlphaStatesResultCriterion(SECriterion criterion, PracticeDto dto) {
        if (criterion.getState() != null){ 
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getConditions().getResults().getAlphaStates().add(alphaState);
        }
        
    }

    /**
     * Map WorkProductsLevelOfDetail to SEPractice. This method not map the
     * Workproduct associated to the current levelOfDetail.
     * 
     * @param workProductsLevelofDetail
     * @param criterionList
     */
    private static void mapResultWorkProductLevelOfDetailEntryCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, Collection<SECriterion> criterionList) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            validateWorkProductLevelOfDetailCriterion(criterionList, element);
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail( (SELevelOfDetail) repoUtil.getDocument(element.getIdLevelOfDetail(), SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SEEntryCriterion.class);
            repoUtil.mongoTemplate.save(seCriterion);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map WorkProductsLevelOfDetail to SEPractice. This method not map the
     * Workproduct associated to the current levelOfDetail.
     * 
     * @param workProductsLevelofDetail
     * @param criterionList
     */
    private static Object mapResultWorkProductLevelOfDetailResultCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, Collection<SECriterion> criterionList) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            validateWorkProductLevelOfDetailCriterion(criterionList, element);
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail( (SELevelOfDetail) repoUtil.getDocument(element.getIdLevelOfDetail(), SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SEEntryCriterion.class);
            repoUtil.mongoTemplate.save(seCriterion);
            criterionList.add(seCriterion);
        });
        return criterionList;
    }

    /**
     * Map Other Conditions to SEPractice
     * 
     * @param otherConditionsList
     * @param criterionList
     */
    private static void mapResultOtherEntryConditions(List<String> otherConditionsList,
            Collection<SECriterion> criterionList) {
        Optional.of(otherConditionsList).ifPresent(conditions -> {
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setOtherConditions(otherConditionsList);
            }, SEEntryCriterion.class);
            repoUtil.mongoTemplate.save(seCriterion);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map Other Conditions to SEPractice
     * 
     * @param otherConditionsList
     * @param criterionList
     */
    private static void mapResultOtherResultConditions(List<String> otherConditionsList,
            Collection<SECriterion> criterionList) {
        Optional.of(otherConditionsList).ifPresent(conditions -> {
            SECompletionCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setOtherConditions(otherConditionsList);
            }, SECompletionCriterion.class);
            repoUtil.mongoTemplate.save(seCriterion);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map Things to Work With from PracticeDto to SEPractice
     * 
     * @param thingsToWorkWith
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapThingsToWorkWith(ThingsToWorkWith thingsToWorkWith, SEPractice to) {
        List<String> alphasIdsList = thingsToWorkWith.getAlphas();
        Objects.requireNonNull(alphasIdsList, "You must select at least one alpha");
        alphasIdsList.forEach(alphaId -> {
            SEAlpha seAlpha = (SEAlpha) repoUtil.getDocument(alphaId, SEAlpha.class);
            // sub-alpha not yet mapped, if it is mandatory to map the subalphas
            // id, do it here or consider to map in the same list of
            // alphaSelectionList
            // No debería construir, debería recuperar
            to.getReferredElements().add(seAlpha);
        });
        mapIdsWorkProducts(to, thingsToWorkWith.getWorkProducts());
        return to;
    }

    private static void mapIdsWorkProducts(SEPractice to, List<String> workProductIdList) {
        Optional.of(workProductIdList).ifPresent(idsList -> {
            idsList.forEach(workProductId -> {
                SEWorkProduct seWorkProduct = (SEWorkProduct) repoUtil.getDocument(workProductId, SEWorkProduct.class);
                to.getReferredElements().add(seWorkProduct);
            });
        });
    }

    /**
     * Map Things to Do from PracticeDto to a SEPractice
     * 
     * @param thingsToDo
     * @param to
     * @return SEPractice
     */
    /*
    public static SEPractice mapThingsToDo(ThingsToDo thingsToDo, SEPractice to) {
        idActivities = new ArrayList<>();
        Optional.of(thingsToDo.getActivities()).orElseThrow(SekcException::new).forEach(activity -> {
            SEActivity seActivity = EntityBuilder.build(act -> {
                act.setName(activity.getName());
                act.setBriefDescription(activity.getBriefDesciption());
                act.setDescription(activity.getDescription());
                act.setRequiredCompetencyLevel(new ArrayList<>());
                act.setApproach(new ArrayList<>());
                act.setAction(new ArrayList<>());
                act.setGoJsPosition(activity.getGoJsPosition());
                act.setCriterion(new ArrayList<>());
                act.setResource(new ArrayList<>());
                act.setActivityAssociation(new ArrayList<>());
                mapCompetencyLevel(activity, act);
                mapApproach(activity, act);
                mapActions(activity, act);
                mapActivityResources(activity, act);
                //map  entryCriterionentryCriterion  completitionCriterion # TODO
            }, SEActivity.class);
            if (activity.getCreated()) // so if it's an update, override existing db document
                seActivity.setId(activity.getIdActivity());
            repoUtil.mongoTemplate.save(seActivity);
            idActivities.add(seActivity.getId());
            
            SEActivitySpace seActivitySpace = (SEActivitySpace) repoUtil.getDocument(activity.getIdActivitySpace(), SEActivitySpace.class);
            SEActivityAssociation seActivityAssociation = EntityBuilder.build(actAssociation -> {
                actAssociation.setEnd1(seActivitySpace);
                actAssociation.setEnd2(seActivity);
            }, SEActivityAssociation.class);
            repoUtil.mongoTemplate.save(seActivityAssociation);
            to.getOwnedElements().add(seActivityAssociation);
        });
        mapActivitiesComposition(thingsToDo.getActivities());
        return to;
    }
    
    public static void mapActivitiesComposition(List<Activity> activities){
        for (int i=0; i< activities.size(); i++){
            SEActivity act = (SEActivity) repoUtil.getDocument( idActivities.get(i), SEActivity.class);
            if (!activities.get(i).getTo().isEmpty()){
                for (String to: activities.get(i).getTo()){
                    //creamos la relación entre actividades
                    SEActivity act2 = (SEActivity) repoUtil.getDocument( idActivities.get(Integer.parseInt(to)), SEActivity.class);
                    if (act2 != null){
                        SEActivityAssociation actAssociation = new SEActivityAssociation();
                        actAssociation.setEnd1(act);
                        actAssociation.setEnd2(act2);
                        repoUtil.mongoTemplate.save(actAssociation);
                        act.getActivityAssociation().add(actAssociation);
                        repoUtil.mongoTemplate.save(act);
                    }
                }
            }
        }
    }
*/
    public static void mapActivityResources(Activity activity, SEActivity act) {
        Optional.of(activity.getResources()).ifPresent(resourceList -> {
            resourceList.forEach(resource -> {
                SEResource seresource = EntityBuilder.build(seResource -> {
                    seResource.setContent(resource.getContent());
                    seResource.setFileName(resource.getFile());
                    seResource.setIdResourceType(ResourcesTypes.valueOf(resource.getIdTypeResource()));
                }, SEResource.class);
                repoUtil.mongoTemplate.save(seresource);
                act.getResource().add(seresource);
            });
        });
    }

    public static void mapActions(Activity activity, SEActivity act) {
        Optional.of(activity.getActions()).ifPresent(actionList -> {
            actionList.forEach(action -> {
                SEAction seaction = EntityBuilder.build(seAction -> {
                    seAction.setKind(ActionKind.valueOf(action.getIdActionKind()));
                    seAction.setAlpha(new ArrayList<>());
                    seAction.setWorkProduct(new ArrayList<>());
                    mapAlphaStateToAction(action.getAlphaStates(), seAction);
                    mapLevelOfDetailToAction(action.getWorkProductsLevelofDetail(), seAction);
                }, SEAction.class);
                repoUtil.mongoTemplate.save(seaction);
                act.getAction().add(seaction);
            });
        });
    }

    public static void mapAlphaStateToAction(List<AlphaState> alphaStates, SEAction seAction) {
        Optional.of(alphaStates).ifPresent(alphaStateList -> {
            alphaStateList.forEach(alphaState -> {
                seAction.getAlpha().add( (SEAlpha) repoUtil.getDocument(alphaState.getIdAlpha(), SEAlpha.class));
            });
        });
    }

    public static void mapLevelOfDetailToAction(List<WorkProductsLevelofDetail> workProductsLevelofDetail,
            SEAction seAction) {
        Optional.of(workProductsLevelofDetail).ifPresent(workProductsLevelofDetailList -> {
            workProductsLevelofDetailList.forEach(workProduct -> {
                seAction.getWorkProduct().add( (SEWorkProduct) repoUtil.getDocument(workProduct.getIdWorkProduct(), SEWorkProduct.class));
            });
        });
    }

    private static void mapApproach(Activity activity, SEActivity act) {
        Optional.of(activity.getApproaches()).ifPresent(approachList -> {
            approachList.forEach(approach -> {
                SEApproach seapproach = EntityBuilder.build(seApproach -> {
                    seApproach.setName(approach.getName());
                    seApproach.setDescription(approach.getDescription());
                }, SEApproach.class);
                repoUtil.mongoTemplate.save(seapproach);
                act.getApproach().add(seapproach);
            });
        });
    }

    private static void mapCompetencyLevel(Activity activity, SEActivity act) {
        Optional.of(activity.getCompetencies()).ifPresent(competencyList -> {
            competencyList.forEach(competency -> {
                act.getRequiredCompetencyLevel().add( (SECompetencyLevel) repoUtil.getDocument(competency.getIdCompetencyLevel(), SECompetencyLevel.class));
                    // if you must map competencyid do it here
            });
        });
    }

    public static void mapRelatedPractices(SEPractice entity, PracticeDto dto) {
        //relatedPractices are optional on the client
        dto.setRelatedPractices( new ArrayList<>());
        for (SEPractice relatedPractice : EssenceFilter.filterLanguageElement( entity.getReferredElements(), SEPractice.class ) ) {
            dto.getRelatedPractices().add(relatedPractice.getId());
        }
    }

}
