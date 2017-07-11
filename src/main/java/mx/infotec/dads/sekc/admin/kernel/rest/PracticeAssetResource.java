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
import mx.infotec.dads.sekc.admin.kernel.service.PracticeAssetService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class PracticeAssetResource {
    
    @Autowired
    private PracticeAssetService practiceAssetService;
    
    @PostMapping("/practiceAssets/")
    public ResponseEntity practiceAssetCreate( @RequestBody Object practiceAsset ){
        ResponseWrapper responseData;
        
        responseData = practiceAssetService.save(practiceAsset);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }

    @GetMapping(value = { "/practiceAssets/","/practiceAssets/{id}" })
    public ResponseEntity practiceAssetGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = practiceAssetService.findOne(id, includeFields);    
        else
            responseData = practiceAssetService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/practiceAssets/{id}")
    public ResponseEntity practiceAssetDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = practiceAssetService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
}
