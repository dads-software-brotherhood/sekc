package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEMethod;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.KernelService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class KernelServiceImpl implements KernelService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEKernelRepository kernelRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(KernelServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getKernelFromRequest(Object kernel, SEKernel kernelToPersistence){
        try{
            Map< String , Object > kernelMap = (Map< String , Object >) kernel;
            //TODO: repositoryUtil.fillSEElementGroupFields(kernelToPersistence, kernelMap);
            
            if (kernelMap.containsKey("referringMethod")){
                List<SEMethod> referringMethod = repositoryUtil.getDocuments((ArrayList<String>) kernelMap.get("referringMethod"), SEMethod.class);
                if (!referringMethod.isEmpty())
                    kernelToPersistence.setReferringMethod(referringMethod);
            }
            kernelToPersistence.setConsistencyRules((String) kernelMap.get("consistencyRules"));
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    // TODO @Override
    public ResponseWrapper save(Object kernel) {
        SEKernel kernelToPersistence = new SEKernel();
        response = new ResponseWrapper();
        if (!getKernelFromRequest(kernel, kernelToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            kernelRepository.save(kernelToPersistence);
            response.setResponseObject(kernelToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(kernelToPersistence) ){
        //    response.setError_message( "Invalid kernel object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEKernel> docCollection = kernelRepository.findAll();
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
        Object document = kernelRepository.findOne(id);
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
        response.setResponseObject(kernelRepository.findOne(id));
        kernelRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
