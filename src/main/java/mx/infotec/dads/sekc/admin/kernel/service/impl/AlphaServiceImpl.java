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
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaDto;
import mx.infotec.dads.sekc.admin.kernel.dto.State;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.AlphaResource;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.AlphaService;
import mx.infotec.dads.sekc.admin.kernel.dto.Action;
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaAssociation;
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaContainment;
import mx.infotec.dads.sekc.admin.kernel.dto.WorkProductManifest;
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
    
    private boolean getAlphaFromRequest(AlphaDto alphaDto, SEAlpha alphaToPersistence){
        try{
            repositoryUtil.fillSEBasicElementFields(alphaToPersistence, alphaDto);
            
            alphaDto.getStates().stream().map((state) -> (SEState) repositoryUtil.getDocument(state.getIdState(), SEState.class)).filter((seState) -> (seState != null)).forEachOrdered((seState) -> {
                alphaToPersistence.getStates().add(seState);
            });
            
            alphaDto.getAction().stream().map((action) -> (SEAction) repositoryUtil.getDocument(action.getIdAction(), SEAction.class)).filter((seAction) -> (seAction != null)).forEachOrdered((seAction) -> {
                alphaToPersistence.getAction().add(seAction);
            });
            
            SEActivitySpace seActivitySpace = (SEActivitySpace) repositoryUtil.getDocument(alphaDto.getActivitySpace().getIdActivitySpace(), SEActivitySpace.class);
            if ( seActivitySpace != null)
                alphaToPersistence.setActivitySpace(seActivitySpace);
            
            alphaDto.getAlphaContainment().stream().map((alphaContainment) -> (SEAlphaContainment) repositoryUtil.getDocument(alphaContainment.getIdAlphaContainment(), SEAlphaContainment.class)).filter((seAlphaContainment) -> (seAlphaContainment != null)).forEachOrdered((seAlphaContainment) -> {
                alphaToPersistence.getAlphaContainment().add(seAlphaContainment);
            });
            
            alphaDto.getAlphaAssociation().stream().map((alphaAssociation) -> (SEAlphaAssociation) repositoryUtil.getDocument(alphaAssociation.getIdAlphaAssociation(), SEAlphaAssociation.class)).filter((seAlphaAssociation) -> ( seAlphaAssociation != null)).forEachOrdered((seAlphaAssociation) -> {
                alphaToPersistence.getAlphaAssociation().add(seAlphaAssociation);
            });
            alphaDto.getWorkProductManifest().stream().map((workProductManifest) -> (SEWorkProductManifest) repositoryUtil.getDocument(workProductManifest.getIdWorkProductManifest(), SEWorkProductManifest.class)).filter((seWorkProductManifest) -> (seWorkProductManifest != null)).forEachOrdered((seWorkProductManifest) -> {
                alphaToPersistence.getWorkProductManifest().add(seWorkProductManifest);
            });
            
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(AlphaDto alpha) {
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
        if (alpha != null && alpha.getWorkProductManifest() != null){
            alpha.getWorkProductManifest().forEach((workProductManifest) -> {
                workProductList.add(workProductManifest.getWorkProduct());
            });
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