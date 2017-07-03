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

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.repository.SEStateRepository;
import mx.infotec.dads.sekc.web.rest.util.RandomUtil;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api")
public class StatesRestService {

    @Autowired
    private SEStateRepository stateRepository;

    private final Logger LOG = LoggerFactory.getLogger(StatesRestService.class);

    /*
     * Create Alpha, Method: POST
     */
    @PostMapping("/state")
    public ResponseEntity createState(@RequestBody Object state) {

        Map< String, Object> stateMap;
        SEState stateToPersistence = new SEState();

        /*
         * Any Exception will send a BAD_REQUEST to the FRONT.
         * If everything is correct, send a OK status to the FRONT.
         */
        try {
            stateMap = (Map< String, Object>) state;
            stateToPersistence.setName((String) stateMap.get("name"));
            stateToPersistence.setDescription((String) stateMap.get("description"));

            //After the implementation of the "essence-impl" project, it is a MUST to create a function 
            //to check the Data in the object, to prevent to persiste Practices with Null values in required FIELDS
            //if (ValidationsController.checkValid(stateToPersistence) ){
            //    log.debug( "Invalid Alpha object, it can't be persisted" );
            //    return new ResponseEntity( HttpStatus.BAD_REQUEST );
            //}
            return new ResponseEntity(stateToPersistence, HttpStatus.OK);

        } catch (Exception e) {
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/state")
    //public ResponseEntity createStateGet(@RequestParam("id") String id, @RequestBody (required=false) Object state) {
    public ResponseEntity stateGet(@RequestParam(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields) {
        Object regresar;
        if (id != null){
            regresar = stateRepository.findOne(id);
            // At this point, we can clean & validate the info before response
            if (includeFields != null && regresar != null)
                return new ResponseEntity(RandomUtil.filterResponseFields(regresar, includeFields), HttpStatus.OK);
        }else{
            regresar = stateRepository.findAll();
        }
        return new ResponseEntity( regresar , HttpStatus.OK );
    }
    
    @DeleteMapping("/state")
    public ResponseEntity alphaDelete(@RequestParam("id") String id) {
        stateRepository.delete(id);
        
        return new ResponseEntity( HttpStatus.OK );
    }
}