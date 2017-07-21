package mx.infotec.dads.sekc.util;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Entry;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.Result;
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
        SEPractice to = new SEPractice();
        to.setOwner(EntityBuilder.build(p -> p.setId(from.getIdKernel()), SEPractice.class));
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDesciption());
        to.setDescription(from.getDescription());
        to.setAuthor(from.getAuthor());
        to.setBriefDescription(from.getBriefDesciption());
        to.setConsistencyRules(from.getConsistencyRules());
        to.setKeyWords(from.getKeywords());
        to.setObjective(from.getObjective());
        return to;
    }

    /**
     * Map Conditions from PracticeDto to a SEPractice
     *
     * @param from
     * @return SEAreaOfConcern
     */
    public static SEPractice mapConditions(PracticeDto from, SEPractice to) {
        Conditions conditions = from.getConditions();
        mapEntriesCriterion(conditions.getEntries(), to);
        mapResultsCriterion(conditions.getResults(), to);
        to.setMeasures(conditions.getMeasures());
        return to;
    }

    private static void mapResultsCriterion(List<Result> results, SEPractice to) {
        results.forEach(result -> {
            mapResultAlphaStatesCriterion(result.getAlphaStates(), to);
            mapResultWorkProductLevelOfDetailCriterion(result.getWorkProductsLevelofDetail(), to);
            mapResultOtherConditions(result.getOtherConditions(), to);
        });
    }

    /**
     * Map AlphaStates to SEPractice
     * 
     * @param alphaState
     * @param to
     */
    private static SEPractice mapResultAlphaStatesCriterion(AlphaState alphaState, SEPractice to) {
        Optional.of(alphaState).ifPresent(element -> {
            Objects.requireNonNull(element.getIdAlpha(), "The Alpha's Id can't be null");
            Objects.requireNonNull(to.getEntryCriterion(), "The EntryCriterion can't be null");
            SECriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setState(EntityBuilder.build(seState -> {
                    seState.setId(element.getIdAlpha());
                }, SEState.class));
            }, SECriterion.class);
            to.getEntryCriterion().add(seCriterion);
        });
        return to;
    }

    /**
     * Map WorkProductsLevelOfDetail to SEPractice. This method not map the
     * Workproduct associated to the current levelOfDetail.
     * 
     * @param alphaState
     * @param to
     */
    private static Object mapResultWorkProductLevelOfDetailCriterion(
            WorkProductsLevelofDetail workProductsLevelofDetail, SEPractice to) {
        Optional.of(workProductsLevelofDetail).ifPresent(element -> {
            Objects.requireNonNull(element.getIdLevelOfDetail(), "The LevelOfDetal's Id can't be null");
            Objects.requireNonNull(element.getIdWorkProduct(), "The WorkProduct's Id can't be null");
            Objects.requireNonNull(to.getEntryCriterion(), "The EntryCriterion can't be null");
            SECriterion seCriterion = EntityBuilder.build(criterion -> {
                criterion.setLevelOfDetail(EntityBuilder.build(seLevelOfDetail -> {
                    seLevelOfDetail.setId(element.getIdLevelOfDetail());
                }, SELevelOfDetail.class));
                // If it is necesary to map the idWorkProduct associated to this
                // Level of Detail, do it here.
            }, SECriterion.class);
            to.getEntryCriterion().add(seCriterion);
        });
        return to;
    }

    /**
     * Map Other Conditions to SEPractice
     * 
     * @param alphaState
     * @param to
     */
    private static SEPractice mapResultOtherConditions(List<String> otherConditionsList, SEPractice to) {
        Optional.of(otherConditionsList).ifPresent(conditions -> to.setEntry(otherConditionsList));
        return to;
    }

    private static SEPractice mapEntriesCriterion(List<Entry> entriesList, SEPractice to) {
        return null;

    }

    /**
     * Map Things to Work With from PracticeDto to a SEPractice
     * 
     * @param from
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapThingsToWorkWith(PracticeDto from, SEPractice to) {
        return to;
    }

    /**
     * Map Things to Do from PracticeDto to a SEPractice
     * 
     * @param from
     * @param to
     * @return SEPractice
     */
    public static SEPractice mapThingsToDo(PracticeDto from, SEPractice to) {
        return to;
    }
}
