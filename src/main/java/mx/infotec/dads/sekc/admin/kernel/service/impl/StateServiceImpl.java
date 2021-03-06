package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.repository.SEStateRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.StateDto;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.StateService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

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
    
    private boolean getStateFromRequest(StateDto stateDto, SEState stateToPersistence){
        try{
            repositoryUtil.fillSELaguageElementFields(stateToPersistence, stateDto);
            
            stateToPersistence.setName( stateDto.getName());
            stateToPersistence.setDescription( stateDto.getDescription());
            
            if (stateDto.getCheckListItem() != null){
                stateDto.getCheckListItem().stream().map((checkListItem) -> (SECheckpoint) repositoryUtil.getDocument(checkListItem.getIdCheckPoint(), SECheckpoint.class)).filter((seCheckListItem) -> ( seCheckListItem != null)).forEachOrdered((seCheckListItem) -> {
                    stateToPersistence.getCheckListItem().add(seCheckListItem);
                });
            }
                
            if (stateDto.getSuccessor()!= null){
                SEState seState = (SEState) repositoryUtil.getDocument(stateDto.getSuccessor().getIdSuccessor(), SEState.class);
                if ( seState != null)
                    stateToPersistence.setSuccessor(seState);
            }
            
            if (stateDto.getCriterion() != null){
                stateDto.getCriterion().stream().map((criterion) -> repositoryUtil.getCorrectCriterionDocument( criterion.getType(), criterion.getIdCriterion())).filter((seCriterion) -> ( seCriterion != null)).forEachOrdered((seCriterion) -> {
                    stateToPersistence.getCriterion().add(seCriterion);
                });
            }
            
            if (stateDto.getAlpha() != null){
                SEAlpha alpha = (SEAlpha) repositoryUtil.getDocument( stateDto.getAlpha().getIdAlpha(), SEAlpha.class);
                if (alpha != null)
                    stateToPersistence.setAlpha(alpha);
            }
            
            if (stateDto.getPredecessor()!= null){
                SEState seState = (SEState) repositoryUtil.getDocument(stateDto.getPredecessor().getIdPredecessor(), SEState.class);
                if ( seState != null)
                    stateToPersistence.setPredecessor(seState);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(StateDto stateDto) {
        SEState stateToPersistence = new SEState();
        response = new ResponseWrapper();
        if (!getStateFromRequest(stateDto, stateToPersistence)){
            response.setErrorMessage( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponseCode(HttpStatus.BAD_REQUEST);
        }else{
            stateRepository.save(stateToPersistence);
            response.setResponseObject(stateToPersistence);
            response.setResponseCode(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(stateToPersistence) ){
        //    response.setErrorMessage( "Invalid state object, it can't be persisted" );
        //    response.setResponseCode(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SEState> docCollection = stateRepository.findAll();
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
        Object document = stateRepository.findOne(id);
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
        response.setResponseObject(stateRepository.findOne(id));
        stateRepository.delete(id);
        response.setResponseCode(HttpStatus.OK);
        return response;
    }
    
}
