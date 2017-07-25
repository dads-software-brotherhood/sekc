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
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.repository.SELevelOfDetailRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.CheckListItem;
import mx.infotec.dads.sekc.admin.kernel.dto.Criterion;
import mx.infotec.dads.sekc.admin.kernel.dto.LevelOfDetailDto;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.LevelOfDetailService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class LevelOfDetailServiceImpl implements LevelOfDetailService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SELevelOfDetailRepository levelOfDetailRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(LevelOfDetailServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getLevelOfDetailFromRequest(LevelOfDetailDto levelOfDetailDto, SELevelOfDetail levelOfDetailToPersistence){
        try{
            repositoryUtil.fillSELaguageElementFields(levelOfDetailToPersistence, levelOfDetailDto);
            
            levelOfDetailToPersistence.setName( levelOfDetailDto.getName());
            
            levelOfDetailToPersistence.setDescription(levelOfDetailDto.getDescription());
            levelOfDetailToPersistence.setSufficientLevel((boolean) levelOfDetailDto.getIsSufficientLevel());
            
            if (levelOfDetailDto.getCheckListItem() != null){
                for (CheckListItem checkpoint: levelOfDetailDto.getCheckListItem()){
                    SECheckpoint checkListItem =  (SECheckpoint) repositoryUtil.getDocument( checkpoint.getIdCheckPoint(), SECheckpoint.class);
                    if ( checkListItem != null)
                        levelOfDetailToPersistence.getCheckListItem().add(checkListItem);
                }
            }
            
            if (levelOfDetailDto.getSuccessor()!= null){
                SELevelOfDetail successor = (SELevelOfDetail) repositoryUtil.getDocument( levelOfDetailDto.getSuccessor().getIdSuccessor(), SELevelOfDetail.class);
                if ( successor != null)
                    levelOfDetailToPersistence.setSuccessor(successor);
            }
            
            if (levelOfDetailDto.getCriterion() != null){
                for (Criterion criterion : levelOfDetailDto.getCriterion()){
                SECriterion seCriterion = (SECriterion) repositoryUtil.getDocument( criterion.getIdCriterion(), SECriterion.class);
                if (seCriterion != null)
                    levelOfDetailToPersistence.getCriterion().add(seCriterion);
                }
            }
            
            if (levelOfDetailDto.getPredecessor()!= null){
                SELevelOfDetail predecessor = (SELevelOfDetail) repositoryUtil.getDocument( levelOfDetailDto.getPredecessor().getIdPredecessor(), SELevelOfDetail.class);
                if ( predecessor != null)
                    levelOfDetailToPersistence.setPredecessor(predecessor);
            }
            
            if (levelOfDetailDto.getWorkProduct() != null){
                SEWorkProduct workProduct = (SEWorkProduct) repositoryUtil.getDocument( levelOfDetailDto.getWorkProduct().getIdWorkProduct(), SEWorkProduct.class);
                if ( workProduct != null)
                    levelOfDetailToPersistence.setWorkProduct(workProduct);
            }
            
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(LevelOfDetailDto levelOfDetailDto) {
        SELevelOfDetail levelOfDetailToPersistence = new SELevelOfDetail();
        response = new ResponseWrapper();
        if (!getLevelOfDetailFromRequest(levelOfDetailDto, levelOfDetailToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            levelOfDetailRepository.save(levelOfDetailToPersistence);
            response.setResponseObject(levelOfDetailToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(levelOfDetailToPersistence) ){
        //    response.setError_message( "Invalid levelOfDetail object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SELevelOfDetail> docCollection = levelOfDetailRepository.findAll();
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
        Object document = levelOfDetailRepository.findOne(id);
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
        response.setResponseObject(levelOfDetailRepository.findOne(id));
        levelOfDetailRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
