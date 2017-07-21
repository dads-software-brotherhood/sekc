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
import mx.infotec.dads.sekc.admin.kernel.dto.Action;
import mx.infotec.dads.sekc.admin.kernel.dto.LevelOfDetail;
import mx.infotec.dads.sekc.admin.kernel.dto.WorkProductDto;
import mx.infotec.dads.sekc.admin.kernel.dto.WorkProductManifest;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.WorkProductService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

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
    
    private boolean getWorkProductFromRequest(WorkProductDto workProductDto, SEWorkProduct workProductToPersistence){
        try{
            repositoryUtil.fillSEBasicElementFields(workProductToPersistence, workProductDto);
            
            if (workProductDto.getLevelOfDetail() != null){
                workProductDto.getLevelOfDetail().stream().map((levelOfDetail) -> (SELevelOfDetail) repositoryUtil.getDocument( levelOfDetail.getIdLevelOfDetail(), SELevelOfDetail.class)).filter((seLevelOfDetail) -> ( seLevelOfDetail != null)).forEachOrdered((seLevelOfDetail) -> {
                    workProductToPersistence.getLevelOfDetail().add(seLevelOfDetail);
            });}
            
            if ( workProductDto.getAction() != null){
                workProductDto.getAction().stream().map((action) -> (SEAction) repositoryUtil.getDocument(action.getIdAction(), SEAction.class)).filter((seAction) -> ( seAction != null)).forEachOrdered((seAction) -> {
                    workProductToPersistence.getAction().add(seAction);
            });}
            
            if (workProductDto.getWorkProductManifest() != null){
                workProductDto.getWorkProductManifest().stream().map((workProductManifest) -> (SEWorkProductManifest) repositoryUtil.getDocument(workProductManifest.getIdWorkProductManifest(), SEWorkProductManifest.class)).filter((seWorkProductManifest) -> ( seWorkProductManifest != null)).forEachOrdered((seWorkProductManifest) -> {
                    workProductToPersistence.getWorkProductManifest().add(seWorkProductManifest);
            });}
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(WorkProductDto workProduct) {
        SEWorkProduct workProductToPersistence = new SEWorkProduct();
        response = new ResponseWrapper();
        if (!getWorkProductFromRequest(workProduct, workProductToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
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
            response.setError_message( ErrorConstants.ERR_RECORD_NOT_FOUND);
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
            response.setError_message( ErrorConstants.ERR_RECORD_NOT_FOUND);
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
