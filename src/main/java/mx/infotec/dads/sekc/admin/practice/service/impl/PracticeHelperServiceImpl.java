package mx.infotec.dads.sekc.admin.practice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeHelperService;

/**
 * PracticeHelperServiceImpl used to store a practice
 * 
 * @author Daniel Cortes Pichardo
 */
@Service
public class PracticeHelperServiceImpl implements PracticeHelperService {

    @Autowired
    private SEPracticeRepository repository;

    private final Logger LOGGER = LoggerFactory.getLogger(PracticeHelperServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGeneralInfo(PracticeDto practice) {

        SEPractice sePractice = new SEPractice();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveRelatedPractices(PracticeDto practice) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveKeywords(PracticeDto practice) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveConditions(PracticeDto practice) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveThingsToWorkWith(PracticeDto practice) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveThingsToDo(PracticeDto practice) {
        // TODO Auto-generated method stub

    }

}
