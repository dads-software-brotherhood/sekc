package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEPracticeAsset;
import mx.infotec.dads.essence.repository.SEPracticeAssetRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.PracticeAssetService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class PracticeAssetServiceImpl implements PracticeAssetService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEPracticeAssetRepository practiceAssetRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(PracticeAssetServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getPracticeAssetFromRequest(Object practiceAsset, SEPracticeAsset practiceAssetToPersistence){
        try{
            Map< String , Object > practiceAssetMap = (Map< String , Object >) practiceAsset;
            //TODO: repositoryUtil.fillSEElementGroupFields(practiceAssetToPersistence, practiceAssetMap);
            
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object practiceAsset) {
        SEPracticeAsset practiceAssetToPersistence = new SEPracticeAsset();
        response = new ResponseWrapper();
        if (!getPracticeAssetFromRequest(practiceAsset, practiceAssetToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            practiceAssetRepository.save(practiceAssetToPersistence);
            response.setResponseObject(practiceAssetToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(practiceAssetToPersistence) ){
        //    response.setError_message( "Invalid practiceAsset object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEPracticeAsset> docCollection = practiceAssetRepository.findAll();
        if (!docCollection.isEmpty() ){
            response.setResponse_code(HttpStatus.OK);
            response.setResponseObject(docCollection);
        }else{
            response.setError_message( ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {
        
        response = new ResponseWrapper();
        Object document = practiceAssetRepository.findOne(id);
        if (document != null){
            response.setResponse_code(HttpStatus.OK);
            if (includeFields != null)
                document = RandomUtil.filterResponseFields(document, includeFields);
            response.setResponseObject(document);
        }else{
            response.setError_message( ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(practiceAssetRepository.findOne(id));
        practiceAssetRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
