package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;
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
import mx.infotec.dads.sekc.admin.kernel.service.TypedResourceService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class TypedResource {
    
    @Autowired
    private TypedResourceService typedResourceService;
    
    @PostMapping("/resources/")
    public ResponseEntity typedResourceCreate( @RequestBody Object typedResource ){
        ResponseWrapper responseData;
        
        responseData = typedResourceService.save(typedResource);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }

    @GetMapping(value = { "/resources/","/resources/{id}" })
    public ResponseEntity typedResourceGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = typedResourceService.findOne(id, includeFields);    
        else
            responseData = typedResourceService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/resources/{id}")
    public ResponseEntity typedResourceDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = typedResourceService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
}
