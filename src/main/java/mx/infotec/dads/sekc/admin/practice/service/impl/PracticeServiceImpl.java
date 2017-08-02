package mx.infotec.dads.sekc.admin.practice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;

import mx.infotec.dads.sekc.util.SEEssenceMapper;
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

    private boolean getPracticeFromRequest(PracticeDto practiceDto, SEPractice sePractice, ResponseWrapper response) {
        try {
            sePractice = SEEssenceMapper.mapSEPractice(practiceDto, repoUtils);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            response.setError_message(e.getMessage());
            LOG.debug("Fail to map Pactice DTO ", e);
            return false;
        }
    }

    @Override
    public ResponseWrapper save(PracticeDto practiceDto) {
        SEPractice sePractice = new SEPractice();
        response = new ResponseWrapper();
        if (!getPracticeFromRequest(practiceDto, sePractice, response)) {
            //Now we use error messages from exceptions inside dtoMapper
            //response.setError_message(ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        } else {
            practiceRepository.save(sePractice);
            response.setResponseObject(sePractice);
            response.setResponse_code(HttpStatus.OK);
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

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEPractice> docCollection = practiceRepository.findAll();
        if (!docCollection.isEmpty()) {
            response.setResponse_code(HttpStatus.OK);
            response.setResponseObject(docCollection);
        } else {
            response.setError_message(ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {

        response = new ResponseWrapper();
        Object document = practiceRepository.findOne(id);
        if (document != null) {
            response.setResponse_code(HttpStatus.OK);
            if (includeFields != null)
                document = RandomUtil.filterResponseFields(document, includeFields);
            response.setResponseObject(document);
        } else {
            response.setError_message(ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(practiceRepository.findOne(id));
        practiceRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }

}
