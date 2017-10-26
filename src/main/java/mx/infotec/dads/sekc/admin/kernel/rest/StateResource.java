package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.StateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.StateService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(API_PATH)
public class StateResource {

    @Autowired
    private StateService stateService;
    
    private static final String ENTITY_NAME = "state";

    @PostMapping("/states")
    @Timed
    public ResponseEntity createState(@RequestBody StateDto state) {
        ResponseWrapper responseData;
        responseData = stateService.save(state);
        
        if (responseData.getErrorMessage().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, state.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, state.toString()))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/states/{id}")
    @Timed
    public ResponseEntity deleteState(@PathVariable("id") String id) {
        ResponseWrapper responseData = stateService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_state_delete"))
                .body(responseData.getResponseObject());
    }

    @GetMapping(value = { "/states/{id}" })
    @Timed
    public ResponseEntity getState(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = stateService.findOne(id, includeFields);    
        else
            responseData = stateService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_state_get", "Error al obtener State"))
            .body(responseData.toString());
    }
    
    @GetMapping(value = { "/states" })
    @Timed
    public ResponseEntity getAllStates( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
            responseData = stateService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, ""))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_state_get", "Error al obtener State"))
            .body(responseData.toString());
    }
    
}