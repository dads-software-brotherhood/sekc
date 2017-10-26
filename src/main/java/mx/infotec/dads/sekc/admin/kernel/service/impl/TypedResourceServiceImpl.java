package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import mx.infotec.dads.essence.model.userdefinedtypes.SETypedResource;
import mx.infotec.dads.essence.model.userdefinedtypes.SEUserDefinedType;
import mx.infotec.dads.essence.repository.SETypedResourceRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.TypedResourceService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class TypedResourceServiceImpl implements TypedResourceService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SETypedResourceRepository typedResourceRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(TypedResourceServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getTypedResourceFromRequest(Object typedResource, SETypedResource typedResourceToPersistence){
        try{
            Map< String , Object > typedResourceMap = (Map< String , Object >) typedResource;
            
            repositoryUtil.fillSEResourceFields(typedResourceToPersistence, typedResourceMap);
            
            if (typedResourceMap.containsKey("kind") && typedResourceMap.get("kind") != null ){
                SEUserDefinedType kind = (SEUserDefinedType) repositoryUtil.getDocument((String) typedResourceMap.get("kind"), SEUserDefinedType.class);
                if (kind != null)
                    typedResourceToPersistence.setKind( kind);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object typedResource) {
        SETypedResource typedResourceToPersistence = new SETypedResource();
        response = new ResponseWrapper();
        if (!getTypedResourceFromRequest(typedResource, typedResourceToPersistence)){
            response.setErrorMessage( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponseCode(HttpStatus.BAD_REQUEST);
        }else{
            typedResourceRepository.save(typedResourceToPersistence);
            response.setResponseObject(typedResourceToPersistence);
            response.setResponseCode(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(typedResourceToPersistence) ){
        //    response.setErrorMessage( "Invalid typedResource object, it can't be persisted" );
        //    response.setResponseCode(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SETypedResource> docCollection = typedResourceRepository.findAll();
        if (!docCollection.isEmpty() ){
            response.setResponseCode(HttpStatus.OK);
            response.setResponseObject(docCollection);
        }else{
            response.setErrorMessage( ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponseCode(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {
        
        response = new ResponseWrapper();
        Object document = typedResourceRepository.findOne(id);
        if (document != null){
            response.setResponseCode(HttpStatus.OK);
            if (includeFields != null)
                document = RandomUtil.filterResponseFields(document, includeFields);
            response.setResponseObject(document);
        }else{
            response.setErrorMessage( ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponseCode(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(typedResourceRepository.findOne(id));
        typedResourceRepository.delete(id);
        response.setResponseCode(HttpStatus.OK);
        return response;
    }
    
}
