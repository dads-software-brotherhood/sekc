package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.AlphaService;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.PathVariable;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;

@RestController
@RequestMapping(API_PATH)
public class AlphaResource {
    
    @Autowired
    private AlphaService alphaService;
    
    private static final String ENTITY_NAME = "alpha";
    
    @PostMapping("/alphas")
    public ResponseEntity alphaCreate( @RequestBody AlphaDto alpha ){
        ResponseWrapper responseData;
        
        responseData = alphaService.save(alpha);
        
        if (responseData.getError_message().equals("")){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, alpha.toString()))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, alpha.toString()))
            .body(responseData.toString());
    }

    @GetMapping(value = { "/alphas","/alphas/{id}" })
    public ResponseEntity alphaGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        
        ResponseWrapper responseData;
        if ( id != null)
            responseData = alphaService.findOne(id, includeFields);    
        else
            responseData = alphaService.findAll(pageable);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_alpha_get", "Error al obtener Alpha"))
            .body(responseData.toString());
    }
    
    @GetMapping("/alphas/{id}/workproducts" )
    public ResponseEntity workproductsFromAlphaGet(@PathVariable(value="id") String id,
            @ApiParam Pageable pageable) {
        
        ResponseWrapper responseData;
        
        responseData = alphaService.findWorkProductList(id);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_alpha/workproduct_get", "Error al obtener workproduct desde alpha"))
            .body(responseData.toString());
    }
    
    @DeleteMapping("/alphas/{id}")
    public ResponseEntity alphaDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = alphaService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_alpha_delete"))
                .body(responseData.getResponseObject());
    }
    
}