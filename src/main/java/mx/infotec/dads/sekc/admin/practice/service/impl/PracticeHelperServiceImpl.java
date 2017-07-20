package mx.infotec.dads.sekc.admin.practice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public PracticeDto save(PracticeDto practice) {
        
        return null;
    }

}
