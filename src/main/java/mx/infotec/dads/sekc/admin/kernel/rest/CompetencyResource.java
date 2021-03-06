package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.CompetencyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.CompetencyService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;


/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class CompetencyResource {
    
    @Autowired
    private CompetencyService competencyService;
    
    private static final String ENTITY_NAME = "competency";
    
    @PostMapping("/competencies")
    @Timed
    public ResponseEntity createCompetency( @RequestBody CompetencyDto competency ){
        ResponseWrapper responseData;
        
        responseData = competencyService.save(competency);
        
        if (responseData.getErrorMessage().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, competency.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, competency.toString()))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/competencies/{id}")
    @Timed
    public ResponseEntity deleteCompetency(@PathVariable("id") String id) {
        ResponseWrapper responseData = competencyService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_competency_delete"))
                .body(responseData.getResponseObject());
    }

    @GetMapping(value = { "/competencies/{id}" })
    @Timed
    public ResponseEntity getCompetency(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = competencyService.findOne(id, includeFields);    
        else
            responseData = competencyService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_competency_get", "Error al obtener Competency"))
            .body(responseData.toString());
    }
    
    @GetMapping(value = { "/competencies" })
    @Timed
    public ResponseEntity getAllCompetencies( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
            responseData = competencyService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, ""))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_competency_get", "Error al obtener Competency"))
            .body(responseData.toString());
    }
    
}
