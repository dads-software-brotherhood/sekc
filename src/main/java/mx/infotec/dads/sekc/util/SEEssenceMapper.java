package mx.infotec.dads.sekc.util;

import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateConditions;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateGeneralInformation;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateWorkProductLevelOfDetailCriterion;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.omg.essence.model.activityspaceandactivity.ActionKind;

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
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.sekc.admin.practice.dto.Activity;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Criteriable;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;
import mx.infotec.dads.sekc.exception.SekcException;

/**
 * SEEssenceMapper Mapper Used for PracticeDto mapping
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SEEssenceMapper {

    private SEEssenceMapper() {

    }

    /**
     * Map a PracticeDto to a SEPractice
     * 
     * @param from
     * @return SEPractice
     */
    public static SEPractice mapSEPractice(PracticeDto from) {
        validateGeneralInformation(from);
        SEPractice to = EntityBuilder.build(practice -> {
            EssenceMapping.fillPractice(practice);
            mapGeneralInfo(from, practice);
            mapRelatedPractices(from.getRelatedPractices(), practice);
            mapConditions(from.getConditions(), practice);
            mapThingsToWorkWith(from.getThingsToWorkWith(), practice);
            mapThingsToDo(from.getThingsToDo(), practice);
        }, SEPractice.class);
        return to;
    }

    /**
     * Map GeneralInfo of a SEPractice
     * 
     * @param from
     * @param practice
     */
    private static void mapGeneralInfo(PracticeDto from, SEPractice practice) {
        practice.setOwner(EntityBuilder.build(p -> p.setId(from.getIdKernel()), SEKernel.class));
        practice.setName(from.getName());
        practice.setObjective(from.getObjective());
        practice.setBriefDescription(from.getBriefDesciption());
        practice.setDescription(from.getDescription());
        practice.setConsistencyRules(from.getConsistencyRules());
        practice.setAuthor(from.getAuthor());
        practice.setKeyWords(from.getKeywords());
    }

    /**
     * Map Conditions from PracticeDto to a SEPractice
     *
     * @param conditions
     * @return SEAreaOfConcern
     */
    public static SEPractice mapConditions(Conditions conditions, SEPractice to) {
        validateConditions(conditions);
        Objects.requireNonNull(to, "the Practice can not be null");
        mapCriterios(conditions.getEntries(), to.getEntryCriterion(), true);
        mapCriterios(conditions.getResults(), to.getResultCriterion(), false);
        to.setMeasures(conditions.getMeasures());
        return to;
    }

    private static void mapCriterios(Criteriable criteriable, Collection<SECriterion> seCriterionList,
            boolean isEntry) {
        mapAlphaCriterion(criteriable, seCriterionList, isEntry);
        mapWorkProductCriterion(criteriable, seCriterionList, isEntry);
        mapOtherCriterion(criteriable, seCriterionList, isEntry);
    }

    private static void mapOtherCriterion(Criteriable criteriable, Collection<SECriterion> seCriterionList,
            boolean isEntry) {
        Optional.of(criteriable.getOtherConditions()).ifPresent(entry -> {
            if (isEntry) {
                mapResultOtherEntryConditions(entry, seCriterionList);
            } else {
                mapResultOtherResultConditions(entry, seCriterionList);
            }
        });
    }

    private static void mapWorkProductCriterion(Criteriable criteriable, Collection<SECriterion> seCriterionList,
            boolean isEntry) {
        Optional.of(criteriable.getWorkProductsLevelofDetail()).ifPresent(workProducts -> {
            if (isEntry) {
                workProducts.forEach(entry -> mapResultWorkProductLevelOfDetailEntryCriterion(entry, seCriterionList));
            } else {
                workProducts.forEach(entry -> mapResultWorkProductLevelOfDetailResultCriterion(entry, seCriterionList));
            }
        });
    }

    private static void mapAlphaCriterion(Criteriable criteriable, Collection<SECriterion> seCriterionList,
            boolean isEntry) {
        Optional.of(criteriable.getAlphaStates()).ifPresent(alphaStates -> {
            if (isEntry) {
                alphaStates.forEach(entry -> mapResultAlphaStatesEntryCriterion(entry, seCriterionList));
            } else {
                alphaStates.forEach(entry -> mapResultAlphaStatesResultCriterion(entry, seCriterionList));
            }
        });
    }

    /**
     * Map AlphaStates to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultAlphaStatesEntryCriterion(AlphaState alphaState,
            Collection<SECriterion> criterionList) {
        Optional.of(alphaState).ifPresent(element -> {
            Objects.requireNonNull(element.getIdAlpha(), "The Alpha's Id can't be null");
            Objects.requireNonNull(criterionList, "The EntryCriterion can't be null");
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setState(EntityBuilder.build(seState -> {
                    seState.setId(element.getIdState());
                }, SEState.class));
            }, SEEntryCriterion.class);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map AlphaStates to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultAlphaStatesResultCriterion(AlphaState alphaState,
            Collection<SECriterion> criterionList) {
        Optional.of(alphaState).ifPresent(element -> {
            Objects.requireNonNull(element.getIdAlpha(), "The Alpha's Id can't be null");
            Objects.requireNonNull(criterionList, "The EntryCriterion can't be null");
            SECompletionCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setState(EntityBuilder.build(seState -> {
                    seState.setId(element.getIdState());
                }, SEState.class));
            }, SECompletionCriterion.class);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map WorkProductsLevelOfDetail to SEPractice. This method not map the
     * Workproduct associated to the current levelOfDetail.
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultWorkProductLevelOfDetailEntryCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, Collection<SECriterion> criterionList) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            validateWorkProductLevelOfDetailCriterion(criterionList, element);
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail(EntityBuilder.build(seLevelOfDetail -> {
                    seLevelOfDetail.setId(element.getIdLevelOfDetail());
                }, SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SEEntryCriterion.class);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map WorkProductsLevelOfDetail to SEPractice. This method not map the
     * Workproduct associated to the current levelOfDetail.
     * 
     * @param alphaState
     * @param criterionList
     */
    private static Object mapResultWorkProductLevelOfDetailResultCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, Collection<SECriterion> criterionList) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            validateWorkProductLevelOfDetailCriterion(criterionList, element);
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail(EntityBuilder.build(seLevelOfDetail -> {
                    seLevelOfDetail.setId(element.getIdLevelOfDetail());
                }, SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SEEntryCriterion.class);
            criterionList.add(seCriterion);
        });
        return criterionList;
    }

    /**
     * Map Other Conditions to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultOtherEntryConditions(List<String> otherConditionsList,
            Collection<SECriterion> criterionList) {
        Optional.of(otherConditionsList).ifPresent(conditions -> {
            SEEntryCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setOtherConditions(otherConditionsList);
            }, SEEntryCriterion.class);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map Other Conditions to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultOtherResultConditions(List<String> otherConditionsList,
            Collection<SECriterion> criterionList) {
        Optional.of(otherConditionsList).ifPresent(conditions -> {
            SECompletionCriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setOtherConditions(otherConditionsList);
            }, SECompletionCriterion.class);
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
            SEAlpha seAlpha = EntityBuilder.build(alpha -> {
                alpha.setId(alphaId);
            }, SEAlpha.class);
            // sub-alpha not yet mapped, if it is mandatory to map the subalphas
            // id, do it here or consider to map in the same list of
            // alphaSelectionList
            to.getReferredElements().add(seAlpha);
        });
        mapIdsWorkProducts(to, thingsToWorkWith.getWorkProducts());
        return to;
    }

    private static void mapIdsWorkProducts(SEPractice to, List<String> workProductIdList) {
        Optional.of(workProductIdList).ifPresent(idsList -> {
            idsList.forEach(workProductId -> {
                SEWorkProduct seWorkProduct = EntityBuilder.build(wp -> {
                    wp.setId(workProductId);
                }, SEWorkProduct.class);
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
    public static SEPractice mapThingsToDo(ThingsToDo thingsToDo, SEPractice to) {
        Optional.of(thingsToDo.getActivities()).orElseThrow(SekcException::new).forEach(activity -> {
            SEActivity seActivity = EntityBuilder.build(act -> {
                act.setName(activity.getName());
                act.setBriefDescription(activity.getBriefDesciption());
                act.setDescription(activity.getDescription());
                mapCompetencyLevel(activity, act);
                mapApproach(activity, act);
                mapActions(activity, act);
                mapActivityCriterion(activity, act);
                // mapActivityCompletitionCriterion();
                // mapActivityResources();

            }, SEActivity.class);
            SEActivitySpace seActivitySpace = EntityBuilder.build(entity -> entity.setId(activity.getIdActivitySpace()),
                    SEActivitySpace.class);
            SEActivityAssociation seActivityAssociation = EntityBuilder.build(actAssociation -> {
                actAssociation.setEnd1(seActivitySpace);
                actAssociation.setEnd2(seActivity);
            }, SEActivityAssociation.class);
            to.getOwnedElements().add(seActivityAssociation);
        });
        return to;
    }

    private static void mapActivityCriterion(Activity activity, SEActivity act) {
        mapCriterios(activity.getEntryCriterion(), act.getCriterion(), true);
        mapCriterios(activity.getCompletitionCriterion(), act.getCriterion(), false);
    }

    private static void mapActions(Activity activity, SEActivity act) {
        Optional.of(activity.getActions()).ifPresent(actionList -> {
            actionList.forEach(action -> {
                act.getAction().add(EntityBuilder.build(seAction -> {
                    seAction.setKind(ActionKind.valueOf(action.getIdActionKind()));
                    mapAlphaStateToAction(action.getAlphaStates(), seAction);
                    mapLevelOfDetailToAction(action.getWorkProductsLevelofDetail(), seAction);
                }, SEAction.class));
            });
        });
    }

    private static void mapAlphaStateToAction(List<AlphaState> alphaStates, SEAction seAction) {
        Optional.of(alphaStates).ifPresent(alphaStateList -> {
            alphaStateList.forEach(alphaState -> {
                seAction.getAlpha().add(EntityBuilder.build(seAlphaNew -> {
                    seAlphaNew.setId(alphaState.getIdAlpha());
                }, SEAlpha.class));
            });
        });
    }

    private static void mapLevelOfDetailToAction(List<WorkProductsLevelofDetail> workProductsLevelofDetail,
            SEAction seAction) {
        Optional.of(workProductsLevelofDetail).ifPresent(workProductsLevelofDetailList -> {
            workProductsLevelofDetailList.forEach(workProduct -> {
                seAction.getWorkProduct().add(EntityBuilder.build(seWorkProductNew -> {
                    seWorkProductNew.setId(workProduct.getIdWorkProduct());
                }, SEWorkProduct.class));
            });
        });
    }

    private static void mapApproach(Activity activity, SEActivity act) {
        Optional.of(activity.getApproaches()).ifPresent(approachList -> {
            approachList.forEach(approach -> {
                act.getApproach().add(EntityBuilder.build(seApproach -> {
                    seApproach.setName(approach.getName());
                    seApproach.setDescription(approach.getDescription());
                }, SEApproach.class));
            });
        });
    }

    private static void mapCompetencyLevel(Activity activity, SEActivity act) {
        Optional.of(activity.getCompetencies()).ifPresent(competencyList -> {
            competencyList.forEach(competency -> {
                act.getRequiredCompetencyLevel().add(EntityBuilder.build(seCompetencyLeve -> {
                    seCompetencyLeve.setId(competency.getIdCompetencyLevel());
                    // if you must map competencyid do it here
                }, SECompetencyLevel.class));
            });
        });
    }

    /**
     * Map Related practice of the SEPractice
     * 
     * @param practicesIdsList
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapRelatedPractices(List<String> practicesIdsList, SEPractice to) {
        Optional.of(practicesIdsList).ifPresent(practiceList -> {
            practiceList.forEach(practiceId -> {
                SEPractice sePractice = EntityBuilder.build(practice -> {
                    practice.setId(practiceId);
                }, SEPractice.class);
                to.getReferredElements().add(sePractice);
            });
        });
        return to;
    }
}
