package mx.infotec.dads.sekc.admin.practice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeConsultDto;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import mx.infotec.dads.sekc.admin.practice.service.util.PracticeConsultDtoMapper;
import mx.infotec.dads.sekc.admin.practice.service.util.ClassMatcher;
import mx.infotec.dads.sekc.admin.practice.service.util.PracticeDtoMapper;
import mx.infotec.dads.sekc.admin.practice.service.util.SEEssenceMapper;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    private SEPracticeRepository practiceRepository;
    @Autowired
    private RandomRepositoryUtil repoUtils;

    private final Logger LOG = LoggerFactory.getLogger(PracticeServiceImpl.class);
    private ResponseWrapper response;

    private SEPractice getPracticeFromRequest(PracticeDto practiceDto, ResponseWrapper response) {
        try {
            return SEEssenceMapper.mapSEPractice(practiceDto, repoUtils);
        } catch (Exception e) {
            //e.printStackTrace();
            response.setErrorMessage(e.getMessage());
            LOG.debug("Fail to map Pactice DTO ", e);
            return null;
        }
    }

    @Override
    public ResponseWrapper save(PracticeDto practiceDto) {
        response = new ResponseWrapper();
        SEPractice sePractice = getPracticeFromRequest(practiceDto, response);
        if (sePractice == null ) {
            //Now we use error messages from exceptions inside dtoMapper
            //response.setError_message(ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponseCode(HttpStatus.BAD_REQUEST);
        } else {
            practiceRepository.save(sePractice);
            response.setResponseObject(sePractice);
            response.setResponseCode(HttpStatus.OK);
        }

        // After object recreaion, before save it is a MUST to create a function
        // to check the Data in the object
        // if (ValidationsController.checkValid(practiceToPersistence) ){
        // response.setError_message( "Invalid practice object, it can't be
        // persisted" );
        // response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
        // }
        return response;
    }

    /**
     * Get all the repositories.
     *
     * @param pageable
     *            the pagination information
     * @param keywords
     *            the keywords to use as filter
     * @return the list of entities
     */
    @Override
    public Page<PracticeConsultDto> findAll(Pageable pageable, List<String> keywords) {
        LOG.debug("Request to get all Practices");
        
        Page<PracticeConsultDto> practices;
        
        if (!keywords.isEmpty() ) {
            practices = practiceRepository.findByKeyWordsIn(keywords, pageable).map(PracticeConsultDtoMapper::toDto);
            
            if (practices != null )
                return practices;
            
            practices = practiceRepository.findByNameIn(keywords, pageable).map(PracticeConsultDtoMapper::toDto);
            
            if (practices != null )
                return practices;
        }
        
        return practiceRepository.findAll(pageable).map(PracticeConsultDtoMapper::toDto);
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {

        response = new ResponseWrapper();
        Object document = practiceRepository.findOne(id);
        if (document != null) {
            response.setResponseCode(HttpStatus.OK);
            //if (includeFields != null)
            //    document = RandomUtil.filterResponseFields(document, includeFields);
            mx.infotec.dads.sekc.admin.practice.dto.PracticeDto onePractice = 
                    PracticeDtoMapper.toDto((SEPractice) document, repoUtils);
            response.setResponseObject(onePractice);
        } else {
            response.setErrorMessage(ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponseCode(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(practiceRepository.findOne(id));
        practiceRepository.delete(id);
        response.setResponseCode(HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseWrapper update(PracticeDto practiceDto) {
        response = new ResponseWrapper();
        
        try{
            // this throws NullPointerException if can't find it
            repoUtils.getDocument(practiceDto.getId(), SEPractice.class);
            
            SEPractice sePractice = getPracticeFromRequest(practiceDto, response);
            if (sePractice == null ) {
                //Now we use error messages from exceptions inside dtoMapper
                response.setErrorMessage(ErrorConstants.ERR_RECORD_NOT_FOUND);
                response.setResponseCode(HttpStatus.NOT_FOUND);
            } else {
                //reemplaza documento existente por completo
                sePractice.setId(practiceDto.getId());
                practiceRepository.save(sePractice);
                response.setResponseObject(sePractice);
                response.setResponseCode(HttpStatus.OK);
            }
        
        } catch (NullPointerException nue){ // if practice's id doesn't exists
            response.setErrorMessage(nue.getMessage());
        }

        // After object recreaion, before save it is a MUST to create a function
        // to check the Data in the object
        // if (ValidationsController.checkValid(practiceToPersistence) ){
        // response.setError_message( "Invalid practice object, it can't be
        // persisted" );
        // response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
        // }
        return response;
    }
}
