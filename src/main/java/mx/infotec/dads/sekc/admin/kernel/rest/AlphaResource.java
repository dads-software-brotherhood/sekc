package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;

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
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.PathVariable;
import mx.infotec.dads.sekc.admin.kernel.service.WorkProductService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

@RestController
@RequestMapping(API_PATH)

public class AlphaResource {
    
    @Autowired
    private WorkProductService alphaService;
    
    @PostMapping("/alphas/")
    public ResponseEntity alphaCreate( @RequestBody Object alpha ){
        ResponseWrapper responseData;
        
        responseData = alphaService.save(alpha);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }

    @GetMapping(value = { "/alphas/","/alphas/{id}" })
    public ResponseEntity alphaGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        
        ResponseWrapper responseData;
        if ( id != null)
            responseData = alphaService.findOne(id, includeFields);    
        else
            responseData = alphaService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/alphas/{id}")
    public ResponseEntity alphaDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = alphaService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
}