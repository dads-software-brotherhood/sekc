package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
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
import mx.infotec.dads.sekc.admin.kernel.service.MethodService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class MethodResource {
    
    @Autowired
    private MethodService methodService;
    
    @PostMapping("/methods/")
    @Timed
    public ResponseEntity createMethod( @RequestBody Object method ){
        ResponseWrapper responseData;
        
        responseData = methodService.save(method);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @DeleteMapping("/methods/{id}")
    @Timed
    public ResponseEntity deleteMethod(@PathVariable("id") String id) {
        ResponseWrapper responseData = methodService.delete(id);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }

    @GetMapping(value = { "/methods/{id}" })
    @Timed
    public ResponseEntity getMethod(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = methodService.findOne(id, includeFields);    
        else
            responseData = methodService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @GetMapping(value = { "/methods/" })
    @Timed
    public ResponseEntity getAllMethods( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
            responseData = methodService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
}
