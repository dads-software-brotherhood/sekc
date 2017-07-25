package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.MethodDto;
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
import mx.infotec.dads.sekc.admin.kernel.service.MethodService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class MethodResource {
    
    @Autowired
    private MethodService methodService;
    
    private static final String ENTITY_NAME = "method";
    
    @PostMapping("/methods/")
    public ResponseEntity methodCreate( @RequestBody MethodDto method ){
        ResponseWrapper responseData;
        
        responseData = methodService.save(method);
        
        if (responseData.getError_message().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, method.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, method.toString()))
            .body(responseData.toString());
    }

    @GetMapping(value = { "/methods/","/methods/{id}" })
    public ResponseEntity methodGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = methodService.findOne(id, includeFields);    
        else
            responseData = methodService.findAll(pageable);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_method_get", "Error al obtener Method"))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/methods/{id}")
    public ResponseEntity methodDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = methodService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_alpha_delete"))
                .body(responseData.getResponseObject());
    }
}
