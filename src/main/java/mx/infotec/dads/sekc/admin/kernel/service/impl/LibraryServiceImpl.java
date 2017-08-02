package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SELibrary;
import mx.infotec.dads.essence.repository.SELibraryRepository;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.LibraryService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SELibraryRepository libraryRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getLibraryFromRequest(Object library, SELibrary libraryToPersistence){
        try{
            Map< String , Object > libraryMap = (Map< String , Object >) library;
            //TODO: repositoryUtil.fillSEElementGroupFields(libraryToPersistence, libraryMap);
            
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(Object library) {
        SELibrary libraryToPersistence = new SELibrary();
        response = new ResponseWrapper();
        if (!getLibraryFromRequest(library, libraryToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            libraryRepository.save(libraryToPersistence);
            response.setResponseObject(libraryToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(libraryToPersistence) ){
        //    response.setError_message( "Invalid library object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SELibrary> docCollection = libraryRepository.findAll();
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
        Object document = libraryRepository.findOne(id);
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
        response.setResponseObject(libraryRepository.findOne(id));
        libraryRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
