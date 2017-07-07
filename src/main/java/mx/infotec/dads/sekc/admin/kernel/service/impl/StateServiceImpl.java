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

import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.repository.SEStateRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.StateService;

/**
 *
 * @author wisog
 */
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEStateRepository stateRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(StateServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getStateFromRequest(Object state, SEState stateToPersistence){
        try{
            Map< String , Object > stateMap = (Map< String , Object >) state;
            repositoryUtil.fillSELaguageElementFields(stateToPersistence, stateMap);
            
            if (stateMap.containsKey("name"))
                stateToPersistence.setName((String) stateMap.get("name"));
            
            if (stateMap.containsKey("description"))
                stateToPersistence.setDescription((String) stateMap.get("description"));
            
            if (stateMap.containsKey("checkListItem")){
                List<SECheckpoint> checkListItem = repositoryUtil.getDocuments((ArrayList<String>) stateMap.get("checkListItem"), SECheckpoint.class);
                if (!checkListItem.isEmpty())
                    stateToPersistence.setCheckListItem(checkListItem);
            }
            
            if (stateMap.containsKey("successor")){
                SEState successor = (SEState) repositoryUtil.getDocument( (String) stateMap.get("successor"), SEState.class);
                if (successor != null)
                    stateToPersistence.setSuccessor(successor);
            }
            
            if (stateMap.containsKey("criterion")){
                List<SECriterion> criterion = repositoryUtil.getDocuments((ArrayList<String>) stateMap.get("criterion"), SECriterion.class);
                if (!criterion.isEmpty())
                    stateToPersistence.setCriterion(criterion);
            }
            
            if (stateMap.containsKey("alpha")){
                SEAlpha alpha = (SEAlpha) repositoryUtil.getDocument( (String) stateMap.get("alpha"), SEAlpha.class);
                if (alpha != null)
                    stateToPersistence.setAlpha(alpha);
            }
            
            if (stateMap.containsKey("predecessor")){
                SEState predecessor = (SEState) repositoryUtil.getDocument( (String) stateMap.get("predecessor"), SEState.class);
                if (predecessor != null)
                    stateToPersistence.setSuccessor(predecessor);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object state) {
        SEState stateToPersistence = new SEState();
        response = new ResponseWrapper();
        if (!getStateFromRequest(state, stateToPersistence)){
            response.setError_message("malformed-no se pudo reconstruir desde request");
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            stateRepository.save(stateToPersistence);
            response.setResponseObject(stateToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(stateToPersistence) ){
        //    response.setError_message( "Invalid state object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEState> docCollection = stateRepository.findAll();
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
        Object document = stateRepository.findOne(id);
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
        response.setResponseObject(stateRepository.findOne(id));
        stateRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
