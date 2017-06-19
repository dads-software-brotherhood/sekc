package mx.infotec.dads.sekc.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.repository.SECheckpointRepository;
import mx.infotec.dads.sekc.web.rest.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Rest Service to CRUD Practices
 *
 * @author alejandro.aguayo
 */
@RestController
@RequestMapping("/api")
public class CheckPointsRestService {

    @Autowired
    private SECheckpointRepository checkPointRepository;

    private final Logger log = LoggerFactory.getLogger(CheckPointsRestService.class);

    /*
	 * Create Practice, Method: POST
     */
    @PostMapping("/checkpoint")
    public ResponseEntity createCheckpoint(@RequestBody Object checkpoint) {

        Map< String, Object> checkPointMap;
        SECheckpoint checkPointToPersistence = new SECheckpoint();

        /*
         * Any Exception will send a BAD_REQUEST to the FRONT.
         * If everything is correct, send a OK status to the FRONT.
         */
        try {
            checkPointMap = (Map< String, Object>) checkpoint;
            checkPointToPersistence.setName((String) checkPointMap.get("name"));
            checkPointToPersistence.setDescription((String) checkPointMap.get("description"));

            //After the implementation of the "essence-impl" project, it is a MUST to create a function 
            //to check the Data in the object, to prevent to persiste Practices with Null values in required FIELDS
            //if (ValidationsController.checkValid(stateToPersistence) ){
            //    log.debug( "Invalid Alpha object, it can't be persisted" );
            //    return new ResponseEntity( HttpStatus.BAD_REQUEST );
            //}
            return new ResponseEntity(checkPointToPersistence, HttpStatus.OK);

        } catch (Exception e) {
            log.debug("Fail to obtain the needed FIELDS ");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/checkpoint")
    public ResponseEntity checkPointGet(@RequestParam(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields) {
        Object regresar;
        if (id != null){
            regresar = checkPointRepository.findOne(id);
            // At this point, we can clean & validate the info before response
            if (includeFields != null && regresar != null)
                return new ResponseEntity(RandomUtil.filterData(regresar, includeFields), HttpStatus.OK);        
        }else{
            regresar = checkPointRepository.findAll();
        }
        
        return new ResponseEntity( regresar , HttpStatus.OK );
    }
    
    @DeleteMapping("/checkpoint")
    public ResponseEntity checkPointDelete(@RequestParam("id") String id) {
        checkPointRepository.delete(id);
        
        return new ResponseEntity( HttpStatus.OK );
    }
    
    @PutMapping("/checkpoint")
    public ResponseEntity checkPointUpdate(@RequestParam("id") String id,
            @RequestParam ("newFields") List<String> newFields) {
        //checkPointRepository.delete(id);
        
        return new ResponseEntity( HttpStatus.OK );
    }

}