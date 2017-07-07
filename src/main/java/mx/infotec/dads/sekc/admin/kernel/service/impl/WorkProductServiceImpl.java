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
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProductManifest;
import mx.infotec.dads.essence.repository.SEWorkProductRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.WorkProductService;

/**
 *
 * @author wisog
 */
@Service
public class WorkProductServiceImpl implements WorkProductService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEWorkProductRepository workProductRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(WorkProductServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getWorkProductFromRequest(Object workProduct, SEWorkProduct workProductToPersistence){
        try{
            Map< String , Object > workProductMap = (Map< String , Object >) workProduct;
            repositoryUtil.fillSEBasicElementFields(workProductToPersistence, workProductMap);
            
            if (workProductMap.containsKey("levelOfDetail")){
                List<SELevelOfDetail> levelOfDetail = repositoryUtil.getDocuments((ArrayList<String>) workProductMap.get("levelOfDetail"), SELevelOfDetail.class);
                if (!levelOfDetail.isEmpty())
                    workProductToPersistence.setLevelOfDetail(levelOfDetail);
            }
            if (workProductMap.containsKey("action")){
                List<SEAction> action = repositoryUtil.getDocuments((ArrayList<String>) workProductMap.get("action"), SEAction.class);
                if (!action.isEmpty())
                    workProductToPersistence.setAction(action);
            }
            if (workProductMap.containsKey("workProductManifest")){
                List<SEWorkProductManifest> workProductManifest = repositoryUtil.getDocuments((ArrayList<String>) workProductMap.get("workProductManifest"), SEWorkProductManifest.class);
                if (!workProductManifest.isEmpty())
                    workProductToPersistence.setWorkProductManifest(workProductManifest);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object workProduct) {
        SEWorkProduct workProductToPersistence = new SEWorkProduct();
        response = new ResponseWrapper();
        if (!getWorkProductFromRequest(workProduct, workProductToPersistence)){
            response.setError_message("malformed-no se pudo reconstruir desde request");
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            workProductRepository.save(workProductToPersistence);
            response.setResponseObject(workProductToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(workProductToPersistence) ){
        //    response.setError_message( "Invalid workProduct object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEWorkProduct> docCollection = workProductRepository.findAll();
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
        Object document = workProductRepository.findOne(id);
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
        response.setResponseObject(workProductRepository.findOne(id));
        workProductRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
