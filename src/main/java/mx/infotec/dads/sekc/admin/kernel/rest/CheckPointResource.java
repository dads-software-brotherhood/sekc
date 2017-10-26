package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.CheckPointDto;
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
import mx.infotec.dads.sekc.admin.kernel.service.CheckPointService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(API_PATH)
public class CheckPointResource {

    @Autowired
    private CheckPointService checkPointService;
    
    private static final String ENTITY_NAME = "checkpoint";
    
    @PostMapping("/checkpoints")
    @Timed
    public ResponseEntity createCheckPoint(@RequestBody CheckPointDto checkPoint) {
        ResponseWrapper responseData;
        responseData = checkPointService.save(checkPoint);
        
        if (responseData.getErrorMessage().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, checkPoint.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, checkPoint.toString()))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/checkpoints/{id}")
    @Timed
    public ResponseEntity deleteCheckPoint(@PathVariable("id") String id) {
        ResponseWrapper responseData = checkPointService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_checpoint_delete"))
                .body(responseData.getResponseObject());
    }

    @GetMapping(value = { "/checkpoints/{id}" })
    @Timed
    public ResponseEntity getCheckPoint(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = checkPointService.findOne(id, includeFields);    
        else
            responseData = checkPointService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_checkpoint_get", "Error al obtener Checkpoint"))
            .body(responseData.toString());
    }
    
    @GetMapping(value = { "/checkpoints" })
    @Timed
    public ResponseEntity getAllCheckPoints( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
        responseData = checkPointService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, ""))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_checkpoint_get", "Error al obtener Checkpoints"))
            .body(responseData.toString());
    }

}