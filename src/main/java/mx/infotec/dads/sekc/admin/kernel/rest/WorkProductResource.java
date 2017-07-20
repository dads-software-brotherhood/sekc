package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.WorkProductDto;
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
import mx.infotec.dads.sekc.admin.kernel.service.WorkProductService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class WorkProductResource {
    
    @Autowired
    private WorkProductService workProductService;
    
    private static final String ENTITY_NAME = "workproduct";
    
    @PostMapping("/workProducts")
    public ResponseEntity workProductCreate( @RequestBody WorkProductDto workProduct ){
        ResponseWrapper responseData;
        
        responseData = workProductService.save(workProduct);
        
        if (responseData.getError_message().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, workProduct.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, workProduct.toString()))
            .body(responseData.toString());
    }

    @GetMapping(value = { "/workProducts","/workProducts/{id}" })
    public ResponseEntity workProductGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = workProductService.findOne(id, includeFields);    
        else
            responseData = workProductService.findAll(pageable);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_workProduct_get", "Error al obtener WorkProduct"))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/workProducts/{id}")
    public ResponseEntity workProductDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = workProductService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_workProduct_delete"))
                .body(responseData.getResponseObject());
    }
}
