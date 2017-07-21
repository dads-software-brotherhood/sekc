package mx.infotec.dads.sekc.admin.practice.service;

import org.springframework.transaction.annotation.Transactional;

import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;

/**
 * PracticeHelperService used for save a practice
 * 
 * @author Daniel Cortes Pichardo
 */
public interface PracticeHelperService {

    /**
     * Save a practice and It's relationships. This method is transactional so
     * it is important to consider for performance issues.
     * 
     * @param practice
     */
    @Transactional
    default public void save(PracticeDto practice) {
        saveGeneralInfo(practice);
        saveRelatedPractices(practice);
        saveKeywords(practice);
        saveConditions(practice);
        saveThingsToWorkWith(practice);
        saveThingsToDo(practice);
    }

    /**
     * Save the Practice's general info
     * 
     * @param practice
     */
    public void saveGeneralInfo(PracticeDto practice);

    /**
     * Save the Practice's related practices
     * 
     * @param practice
     */
    public void saveRelatedPractices(PracticeDto practice);

    /**
     * Save the Practice's keywords
     * 
     * @param practice
     */
    public void saveKeywords(PracticeDto practice);

    /**
     * Save the Practice's conditions
     * 
     * @param practice
     */
    public void saveConditions(PracticeDto practice);

    /**
     * Save the practice's things to work with elements
     * 
     * @param practice
     */
    public void saveThingsToWorkWith(PracticeDto practice);

    /**
     * Save the practice's things to do elements
     * 
     * @param practice
     */
    public void saveThingsToDo(PracticeDto practice);

}
