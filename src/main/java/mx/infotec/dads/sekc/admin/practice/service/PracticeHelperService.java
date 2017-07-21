package mx.infotec.dads.sekc.admin.practice.service;

import org.springframework.transaction.annotation.Transactional;

import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;

/**
 * PracticeHelperService used for save a practice
 * 
 * @author Daniel Cortes Pichardo
 */
public interface PracticeHelperService {

    @Transactional
    default public void save(PracticeDto practice) {
        saveGeneralInfo(practice);
        saveRelatedPractices(practice);
        saveKeywords(practice);
        saveConditions(practice);
        saveThingsToWorkWith(practice);
        saveThingsToDo(practice);
    }

    public void saveGeneralInfo(PracticeDto practice);

    public void saveRelatedPractices(PracticeDto practice);

    public void saveKeywords(PracticeDto practice);

    public void saveConditions(PracticeDto practice);

    public void saveThingsToWorkWith(PracticeDto practice);

    public void saveThingsToDo(PracticeDto practice);

}
