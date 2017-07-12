package mx.infotec.dads.sekc.admin.practice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class CatalogsServiceImpl implements PracticeService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEPracticeRepository practiceRepository;

    private final Logger LOG = LoggerFactory.getLogger(CatalogsServiceImpl.class);
    private ResponseWrapper response;

    private boolean getPracticeFromRequest(PracticeDto practice, SEPractice practiceToPersistence) {
        try {
            Map<String, Object> practiceMap = (Map<String, Object>) practice;
            repositoryUtil.fillSEElementGroupFields(practiceToPersistence, practiceMap);
            practiceToPersistence.setBriefDescription((String) practiceMap.get("consistencyRules"));
            practiceToPersistence.setBriefDescription((String) practiceMap.get("objective"));
            practiceToPersistence.setMeasures((Collection<String>) practiceMap.get("measures"));
            practiceToPersistence.setEntry((Collection<String>) practiceMap.get("entry"));
            practiceToPersistence.setResult((Collection<String>) practiceMap.get("result"));
            practiceToPersistence.setKeyWords((List<String>) practiceMap.get("keyWords"));
            practiceToPersistence.setAuthor((String) practiceMap.get("author"));

            return true;
        } catch (Exception e) {
            LOG.debug("Fail to obtain the needed FIELDS ", e);
            return false;
        }
    }

    @Override
    public ResponseWrapper save(PracticeDto practice) {
        SEPractice practiceToPersistence = new SEPractice();
        response = new ResponseWrapper();
        if (!getPracticeFromRequest(practice, practiceToPersistence)) {
            response.setError_message(ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        } else {
            practiceRepository.save(practiceToPersistence);
            response.setResponseObject(practiceToPersistence);
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
