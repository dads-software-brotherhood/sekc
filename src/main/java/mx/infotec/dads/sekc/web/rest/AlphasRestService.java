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

import mx.infotec.dads.essence.repository.SEAlphaRepository;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.sekc.web.rest.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api")
public class AlphasRestService {
    
    @Autowired
    private SEAlphaRepository alphaRepository;
    
    private final Logger log = LoggerFactory.getLogger( AlphasRestService.class );

    /*
     * Create Alpha, Method: POST
     */
    @PostMapping("/alpha")
    public ResponseEntity createAlpha( @RequestBody Object alpha ){

        Map< String , Object > alphaMap;
        SEAlpha alphaToPersistence = new SEAlpha();

        /*
         * Any Exception will send a BAD_REQUEST to the FRONT.
         * If everything is correct, send a OK status to the FRONT.
         */
        try{
            alphaMap = (Map< String , Object >) alpha;
            alphaToPersistence.setName( (String) alphaMap.get( "name" ) );
            alphaToPersistence.setDescription( (String) alphaMap.get( "description" ) );
            alphaToPersistence.setBriefDescription( (String) alphaMap.get( "briefDescription" ) );

            //After the implementation of the "essence-impl" project, it is a MUST to create a function 
            //to check the Data in the object, to prevent to persiste Practices with Null values in required FIELDS
            //if (ValidationsController.checkValid(stateToPersistence) ){
            //    log.debug( "Invalid Alpha object, it can't be persisted" );
            //    return new ResponseEntity( HttpStatus.BAD_REQUEST );
            //}
            
            return new ResponseEntity( alphaToPersistence , HttpStatus.OK );

        }catch( Exception e ){
            log.debug( "Fail to obtain the needed FIELDS " );
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/alpha")
    public ResponseEntity alphaGet(@RequestParam(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields) {
        
        Object regresar;
        if (id != null){
            regresar = alphaRepository.findOne(id);
            // At this point, we can clean & validate the info before response
            if (includeFields != null && regresar != null)
                return new ResponseEntity(RandomUtil.filterData(regresar, includeFields), HttpStatus.OK);
        }else{
            regresar = alphaRepository.findAll();
        }
        
        return new ResponseEntity( regresar , HttpStatus.OK );
    }
    
    @DeleteMapping("/alpha")
    public ResponseEntity alphaDelete(@RequestParam("id") String id) {
        alphaRepository.delete(id);
        
        return new ResponseEntity( HttpStatus.OK );
    }
}