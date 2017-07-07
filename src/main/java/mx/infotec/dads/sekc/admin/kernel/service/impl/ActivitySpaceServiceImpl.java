package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.repository.SEActivitySpaceRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.ActivitySpaceService;

/**
 *
 * @author wisog
 */
@Service
public class ActivitySpaceServiceImpl implements ActivitySpaceService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEActivitySpaceRepository activitySpaceRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(ActivitySpaceServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getActivitySpaceFromRequest(Object activitySpace, SEActivitySpace activitySpaceToPersistence){
        try{
            Map< String , Object > activitySpaceMap = (Map< String , Object >) activitySpace;
            repositoryUtil.fillSEBasicElementFields(activitySpaceToPersistence, activitySpaceMap);
            
            if (activitySpaceMap.containsKey("input")){
                List<SEAlpha> input = repositoryUtil.getDocuments((ArrayList<String>) activitySpaceMap.get("input"), SEAlpha.class);
                if (!input.isEmpty())
                    activitySpaceToPersistence.setInput(input);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object activitySpace) {
        SEActivitySpace activitySpaceToPersistence = new SEActivitySpace();
        response = new ResponseWrapper();
        if (!getActivitySpaceFromRequest(activitySpace, activitySpaceToPersistence)){
            response.setError_message("malformed-no se pudo reconstruir desde request");
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            activitySpaceRepository.save(activitySpaceToPersistence);
            response.setResponseObject(activitySpaceToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(activitySpaceToPersistence) ){
        //    response.setError_message( "Invalid activitySpace object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEActivitySpace> docCollection = activitySpaceRepository.findAll();
        if (!docCollection.isEmpty() ){
            response.setResponse_code(HttpStatus.OK);
            response.setResponseObject(docCollection);
        }else{
            response.setError_message("No se encontró el elemento"); // Refactor LIST of errors
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {
        
        response = new ResponseWrapper();
        Object document = activitySpaceRepository.findOne(id);
        if (document != null){
            response.setResponse_code(HttpStatus.OK);
            if (includeFields != null)
                document = RandomUtil.filterResponseFields(document, includeFields);
            response.setResponseObject(document);
        }else{
            response.setError_message("No se encontró el elemento"); // Refactor LIST of errors
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(activitySpaceRepository.findOne(id));
        activitySpaceRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
