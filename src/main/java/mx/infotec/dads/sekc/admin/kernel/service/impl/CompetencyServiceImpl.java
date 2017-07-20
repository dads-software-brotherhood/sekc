package mx.infotec.dads.sekc.admin.kernel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.infotec.dads.essence.model.competency.SECompetency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.repository.SECompetencyRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.CompetencyDto;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.CompetencyService;
import mx.infotec.dads.sekc.admin.kernel.dto.PossibleLevel;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;
import org.omg.essence.model.competency.CompetencyLevel;

/**
 *
 * @author wisog
 */
@Service
public class CompetencyServiceImpl implements CompetencyService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SECompetencyRepository competenciesRepository;
    
    private final Logger LOG = LoggerFactory.getLogger(CompetencyServiceImpl.class );
    private ResponseWrapper response;
    
    private boolean getCompetencyFromRequest(CompetencyDto competencyDto, SECompetency competencyToPersistence){
        try{
            repositoryUtil.fillSEBasicElementFields(competencyToPersistence, competencyDto);
            
            if ( competencyDto.getPossibleLevel() != null){
                competencyDto.getPossibleLevel().forEach((possibleLevel) -> {
                    CompetencyLevel sePossibleLevel = (CompetencyLevel) repositoryUtil.getDocument( possibleLevel.getIdPossibleLevel(), SECompetencyLevel.class);
                    if (possibleLevel != null) {
                        competencyToPersistence.getPossibleLevel().add(sePossibleLevel);
                    }
                });
            }   
            
            return true;
        }catch( Exception e ){
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return false;
        }
    }

    @Override
    public ResponseWrapper save(CompetencyDto competencyDto) {
        SECompetency competencyToPersistence = new SECompetency();
        response = new ResponseWrapper();
        if (!getCompetencyFromRequest(competencyDto, competencyToPersistence)){
            response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        }else{
            competenciesRepository.save(competencyToPersistence);
            response.setResponseObject(competencyToPersistence);
            response.setResponse_code(HttpStatus.OK);
        }
        
        //After object recreaion, before save it is a MUST to create a function 
        //to check the Data in the object
        //if (ValidationsController.checkValid(competenciesToPersistence) ){
        //    response.setError_message( "Invalid competencies object, it can't be persisted" );
        //    response.setResponse_code(HttpStatus.BAD_REQUEST);
        //}
        return response;
    }

    @Override
    public ResponseWrapper findAll(Pageable pag) {
        response = new ResponseWrapper();
        List<SECompetency> docCollection = competenciesRepository.findAll();
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
        Object document = competenciesRepository.findOne(id);
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
        response.setResponseObject(competenciesRepository.findOne(id));
        competenciesRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }
    
}
