package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.ActivitySpaceDto;
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

import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.ActivitySpaceService;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class ActivitySpaceResource {
    
    @Autowired
    private ActivitySpaceService activitySpaceService;
    
    private static final String ENTITY_NAME = "activitySpace";
    
    @PostMapping("/activitySpaces")
    @Timed
    public ResponseEntity createActivitySpace( @RequestBody ActivitySpaceDto activitySpace ){
        ResponseWrapper responseData;
        
        responseData = activitySpaceService.save(activitySpace);
        if (responseData.getErrorMessage().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, activitySpace.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, activitySpace.toString()))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/activitySpaces/{id}")
    @Timed
    public ResponseEntity deleteActivitySpace(@PathVariable("id") String id) {
        ResponseWrapper responseData = activitySpaceService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_activity_spaces_delete"))
                .body(responseData.getResponseObject());
    }

    @GetMapping(value = { "/activitySpaces/{id}" })
    @Timed
    public ResponseEntity getActivitySpace(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = activitySpaceService.findOne(id, includeFields);    
        else
            responseData = activitySpaceService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_activity_spaces_get", "Error al obtener Activity Space"))
            .body(responseData.toString());
    } //"/activitySpaces"
    
    @GetMapping(value = { "/activitySpaces" })
    @Timed
    public ResponseEntity getAllActivitySpaces( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
            responseData = activitySpaceService.findAll(pageable);
        
        if (responseData.getErrorMessage().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, ""))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_activity_spaces_get", "Error al obtener Activity Space"))
            .body(responseData.toString());
    }
}
