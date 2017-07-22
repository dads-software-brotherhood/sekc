package mx.infotec.dads.sekc.util;

import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateGeneralInformation;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateConditions;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateWorkProductLevelOfDetailCriterion;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Criteriable;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;

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
            practice.setOwner(EntityBuilder.build(p -> p.setId(from.getIdKernel()), SEKernel.class));
            practice.setName(from.getName());
            practice.setObjective(from.getObjective());
            practice.setBriefDescription(from.getBriefDesciption());
            practice.setDescription(from.getDescription());
            practice.setConsistencyRules(from.getConsistencyRules());
            // {relatedPractice} property not yet mapped
            practice.setAuthor(from.getAuthor());
            practice.setKeyWords(from.getKeywords());
            mapConditions(from.getConditions(), practice);
            mapThingsToWorkWith(from.getThingsToWorkWith(), practice);
            mapThingsToDo(from.getThingsToDo(), practice);
        }, SEPractice.class);
        return to;
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
        mapCriterios(conditions.getEntries(), to.getEntryCriterion());
        mapCriterios(conditions.getResults(), to.getResultCriterion());
        to.setMeasures(conditions.getMeasures());
        return to;
    }

    private static void mapCriterios(Collection<? extends Criteriable> criteriableList,
            Collection<SECriterion> seCriterionList) {
        criteriableList.forEach(entry -> {
            Objects.requireNonNull(seCriterionList, "The SECriterion List can not be null");
            mapResultAlphaStatesCriterion(entry.getAlphaStates(), seCriterionList);
            mapResultWorkProductLevelOfDetailCriterion(entry.getWorkProductsLevelofDetail(), seCriterionList);
            mapResultOtherConditions(entry.getOtherConditions(), seCriterionList);
        });
    }

    /**
     * Map AlphaStates to SEPractice
     * 
     * @param alphaState
     * @param criterionList
     */
    private static void mapResultAlphaStatesCriterion(AlphaState alphaState, Collection<SECriterion> criterionList) {
        Optional.of(alphaState).ifPresent(element -> {
            Objects.requireNonNull(element.getIdAlpha(), "The Alpha's Id can't be null");
            Objects.requireNonNull(criterionList, "The EntryCriterion can't be null");
            SECriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setState(EntityBuilder.build(seState -> {
                    seState.setId(element.getIdAlpha());
                }, SEState.class));
            }, SECriterion.class);
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
    private static Object mapResultWorkProductLevelOfDetailCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, Collection<SECriterion> criterionList) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            validateWorkProductLevelOfDetailCriterion(criterionList, element);
            SECriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail(EntityBuilder.build(seLevelOfDetail -> {
                    seLevelOfDetail.setId(element.getIdLevelOfDetail());
                }, SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SECriterion.class);
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
    private static void mapResultOtherConditions(List<String> otherConditionsList,
            Collection<SECriterion> criterionList) {
        Optional.of(otherConditionsList).ifPresent(conditions -> {
            SECriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setOtherConditions(otherConditionsList);
            }, SECriterion.class);
            criterionList.add(seCriterion);
        });
    }

    /**
     * Map Things to Work With from PracticeDto to a SEPractice
     * 
     * @param thingsToWorkWith
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapThingsToWorkWith(ThingsToWorkWith thingsToWorkWith, SEPractice to) {
        return to;
    }

    /**
     * Map Things to Do from PracticeDto to a SEPractice
     * 
     * @param thingsToDo
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapThingsToDo(ThingsToDo thingsToDo, SEPractice to) {
        return to;
    }
}
