package mx.infotec.dads.sekc.admin.practice.validation;

import java.util.Collection;
import java.util.Objects;

import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;

/**
 * PracticeDtoValidation used for validate de PracticeDto model. Each element
 * that must be validated before to be procesed must be validated agains the
 * rules presented here.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class PracticeDtoValidation {

    private PracticeDtoValidation() {

    }

    public static void validateGeneralInformation(PracticeDto target) {
        Objects.requireNonNull(target, "the practiceDto can not be null");
        Objects.requireNonNull(target.getConditions());
        Objects.requireNonNull(target.getThingsToWorkWith());
        Objects.requireNonNull(target.getThingsToDo());
    }

    public static void validateConditions(Conditions target) {
        Objects.requireNonNull(target, "The conditions Dto can not be null");
        Objects.requireNonNull(target.getResults(), "The Results can not be null");
        Objects.requireNonNull(target.getEntries(), "The Entries can no be nul");
    }

    public static void validateWorkProductLevelOfDetailCriterion(Collection<SECriterion> criterionList,
            WorkProductsLevelofDetail element) {
        Objects.requireNonNull(element.getIdLevelOfDetail(), "The LevelOfDetal's Id can't be null");
        Objects.requireNonNull(element.getIdWorkProduct(), "The WorkProduct's Id can't be null");
        Objects.requireNonNull(criterionList, "The EntryCriterion can't be null");
    }
}
