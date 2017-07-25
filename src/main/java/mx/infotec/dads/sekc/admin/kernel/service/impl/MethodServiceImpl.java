package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEMethod;
import mx.infotec.dads.essence.repository.SEMethodRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.MethodDto;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.MethodService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class MethodServiceImpl implements MethodService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEMethodRepository methodRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(MethodServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getMethodFromRequest(MethodDto methodDto, SEMethod methodToPersistence){
        try{
            repositoryUtil.fillSEElementGroupFields(methodToPersistence, methodDto);
            
            methodToPersistence.setPurpose( methodDto.getPurpose());
            
            if (methodDto.getBaseKernel() != null){
                SEKernel baseKernel =  (SEKernel) repositoryUtil.getDocument( methodDto.getBaseKernel().getIdBaseKernel(), SEKernel.class);
                if ( baseKernel != null)
                    methodToPersistence.setBaseKernel(baseKernel);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(MethodDto methodDto) {
        SEMethod methodToPersistence = new SEMethod();
        response = new ResponseWrapper();
        if (!getMethodFromRequest(methodDto, methodToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            methodRepository.save(methodToPersistence);
            response.setResponseObject(methodToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(methodToPersistence) ){
        //    response.setError_message( "Invalid method object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEMethod> docCollection = methodRepository.findAll();
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
        Object document = methodRepository.findOne(id);
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
        response.setResponseObject(methodRepository.findOne(id));
        methodRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
