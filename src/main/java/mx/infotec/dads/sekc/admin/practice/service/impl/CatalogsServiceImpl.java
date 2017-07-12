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
import mx.infotec.dads.sekc.admin.practice.dto.CatalogsDto;
import mx.infotec.dads.sekc.admin.practice.service.CatalogsService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class CatalogsServiceImpl implements CatalogsService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEPracticeRepository practiceRepository;

    private final Logger LOG = LoggerFactory.getLogger(CatalogsServiceImpl.class);
    private ResponseWrapper response;

   
    @Override
    public ResponseWrapper save(CatalogsDto practice) {
        SEPractice practiceToPersistence = new SEPractice();
        response = new ResponseWrapper();
        

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
