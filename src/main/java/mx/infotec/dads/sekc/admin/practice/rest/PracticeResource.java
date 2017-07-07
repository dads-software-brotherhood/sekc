package mx.infotec.dads.sekc.admin.practice.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class PracticeResource {

    @Autowired
    private PracticeService practiceService;

    @PostMapping("/practices/")
    public ResponseEntity createPractice( @RequestBody Object practice ) {
        
        SEPractice practiceToPersiste = new SEPractice();

        return new ResponseEntity(practiceToPersiste, HttpStatus.OK);
        /*
        try {
            practiceMap = (Map< String, Object>) practice;

            practiceToPersiste.setConsistencyRules((String) practiceMap.get("consistencyRules"));
            practiceToPersiste.setEntry((List<String>) practiceMap.get("entry"));
            practiceToPersiste.setMeasures((List<String>) practiceMap.get("measures"));
            practiceToPersiste.setObjective((String) practiceMap.get("objective"));
            practiceToPersiste.setResult((List<String>) practiceMap.get("result"));
            practiceToPersiste.setBriefDescription((String) practiceMap.get("briefDescription"));
            practiceToPersiste.setDescription((String) practiceMap.get("description"));
            practiceToPersiste.setName((String) practiceMap.get("name"));

            return new ResponseEntity(practiceToPersiste, HttpStatus.OK);

        } catch (Exception e) {
            LOG.debug( "Fail to obtain the needed FIELDS ", e );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }*/
    }

    @GetMapping(value = { "/practices/","/practices/{id}" })
    public ResponseEntity workProductGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = practiceService.findOne(id, includeFields);    
        else
            responseData = practiceService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/practices/{id}")
    public ResponseEntity workProductDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = practiceService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
}