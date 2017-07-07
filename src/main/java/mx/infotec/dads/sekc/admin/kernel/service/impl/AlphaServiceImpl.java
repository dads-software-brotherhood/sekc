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

import mx.infotec.dads.essence.model.activityspaceandactivity.SEAction;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlphaAssociation;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlphaContainment;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProductManifest;
import mx.infotec.dads.essence.repository.SEAlphaRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.AlphaResource;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.AlphaService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;
/**
 *
 * @author wisog
 */
@Service
public class AlphaServiceImpl implements AlphaService {
    
    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEAlphaRepository alphaRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(AlphaResource.class );
    private ResponseWrapper response;
    
    private boolean getAlphaFromRequest(Object alpha, SEAlpha alphaToPersistence){
        try{
            Map< String , Object > alphaMap = (Map< String , Object >) alpha;
            repositoryUtil.fillSEBasicElementFields(alphaToPersistence, alphaMap);
            
            if (alphaMap.containsKey("states")){
                List<SEState> states = repositoryUtil.getDocuments((ArrayList<String>) alphaMap.get("states"), SEState.class);
                if (!states.isEmpty())
                    alphaToPersistence.setStates(states);
            }
            if (alphaMap.containsKey("action")){
                List<SEAction> action = repositoryUtil.getDocuments((ArrayList<String>) alphaMap.get("action"), SEAction.class);
                if (!action.isEmpty())
                    alphaToPersistence.setAction(action);
            }
            if (alphaMap.containsKey("activitySpace")){
                SEActivitySpace activitySpace = (SEActivitySpace) repositoryUtil.getDocument((String) alphaMap.get("activitySpace"), SEActivitySpace.class);
            }
            if (alphaMap.containsKey("alphaContainment")){
                List<SEAlphaContainment> alphaContainment = repositoryUtil.getDocuments((ArrayList<String>) alphaMap.get("alphaContainment"), SEAlphaContainment.class);
                if (!alphaContainment.isEmpty())
                    alphaToPersistence.setAlphaContainment(alphaContainment);
            }
            if (alphaMap.containsKey("alphaAssociation")){
                List<SEAlphaAssociation> alphaAssociation = repositoryUtil.getDocuments((ArrayList<String>) alphaMap.get("alphaAssociation"), SEAlphaAssociation.class);
                if (!alphaAssociation.isEmpty())
                    alphaToPersistence.setAlphaAssociation(alphaAssociation);
            }
            if (alphaMap.containsKey("workProductManifest")){
                List<SEWorkProductManifest> workProductManifest = repositoryUtil.getDocuments((ArrayList<String>) alphaMap.get("workProductManifest"), SEWorkProductManifest.class);
                if (!workProductManifest.isEmpty())
                    alphaToPersistence.setWorkProductManifest(workProductManifest);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object alpha) {
        SEAlpha alphaToPersistence = new SEAlpha();
        response = new ResponseWrapper();
        if (!getAlphaFromRequest(alpha, alphaToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            alphaRepository.save(alphaToPersistence);
            response.setResponseObject(alphaToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(alphaToPersistence) ){
        //    response.setError_message( "Invalid Alpha object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEAlpha> docCollection = alphaRepository.findAll();
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
        Object document = alphaRepository.findOne(id);
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
        response.setResponseObject(alphaRepository.findOne(id));
        alphaRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseWrapper findWorkProductList(String id) {
        response = new ResponseWrapper();
        SEAlpha alpha = (SEAlpha) findOne(id, null).getResponseObject();
        List<SEWorkProduct> workProductList = new ArrayList<>();
        if (alpha != null){
            if (alpha.getWorkProductManifest() != null){
                alpha.getWorkProductManifest().forEach((workProductManifest) -> {
                    workProductList.add(workProductManifest.getWorkProduct());
                });
            }
        }
        response.setResponseObject(workProductList);

        if (workProductList.isEmpty()){
            response.setResponse_code(HttpStatus.NOT_FOUND);
            response.setError_message(ErrorConstants.ERR_RECORD_NOT_FOUND);
        }else
            response.setResponse_code(HttpStatus.OK);
        return response;
    }
}