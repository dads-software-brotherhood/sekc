package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.repository.SECheckpointRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.CheckPointDto;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.CheckPointService;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;


/**
 *
 * @author wisog
 */
@Service
public class CheckPointServiceImpl implements CheckPointService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SECheckpointRepository checkPointRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(CheckPointServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getCheckPointFromRequest(CheckPointDto checkPointDto, SECheckpoint checkPointToPersistence){
        try{
            
            repositoryUtil.fillSELaguageElementFields(checkPointToPersistence, checkPointDto);
            
            checkPointToPersistence.setName( checkPointDto.getName());
            checkPointToPersistence.setDescription( checkPointDto.getDescription());
            checkPointToPersistence.setShortDescription( checkPointDto.getShortDescription());
            
            if (checkPointDto.getLevel() != null){
                SELevelOfDetail level = (SELevelOfDetail) repositoryUtil.getDocument( checkPointDto.getLevel().getIdLevel(), SELevelOfDetail.class);
                if (level != null)
                    checkPointToPersistence.setLevel(level);
            }
            if (checkPointDto.getState() != null){
                SEState state = (SEState) repositoryUtil.getDocument( checkPointDto.getState().getIdState(), SEState.class);
                if (state != null)
                    checkPointToPersistence.setState(state);
            }
            if (checkPointDto.getCompetencyLevel() != null){
                SECompetencyLevel competencyLevel = (SECompetencyLevel) repositoryUtil.getDocument( checkPointDto.getCompetencyLevel().getIdCompetencyLevel(), SECompetencyLevel.class);
                if (competencyLevel != null)
                    checkPointToPersistence.setCompetencyLevel( competencyLevel);
            }
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(CheckPointDto checkPoint) {
        SECheckpoint checkPointToPersistence = new SECheckpoint();
        response = new ResponseWrapper();
        if (!getCheckPointFromRequest(checkPoint, checkPointToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            checkPointRepository.save(checkPointToPersistence);
            response.setResponseObject(checkPointToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(checkPointToPersistence) ){
        //    response.setError_message( "Invalid checkPoint object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SECheckpoint> docCollection = checkPointRepository.findAll();
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
        Object document = checkPointRepository.findOne(id);
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
        response.setResponseObject(checkPointRepository.findOne(id));
        checkPointRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
