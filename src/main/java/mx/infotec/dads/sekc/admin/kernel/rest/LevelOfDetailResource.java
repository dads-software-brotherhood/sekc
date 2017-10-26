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
import mx.infotec.dads.sekc.admin.kernel.service.LevelOfDetailService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class LevelOfDetailResource {
    
    @Autowired
    private LevelOfDetailService levelOfDetailService;
    
    @PostMapping("/levelOfDetails/")
    @Timed
    public ResponseEntity createLevelOfDetail( @RequestBody Object levelOfDetail ){
        ResponseWrapper responseData;
        
        responseData = levelOfDetailService.save(levelOfDetail);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode());
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @DeleteMapping("/levelOfDetails/{id}")
    @Timed
    public ResponseEntity deleteLevelOfDetail(@PathVariable("id") String id) {
        ResponseWrapper responseData = levelOfDetailService.delete(id);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode());
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }

    @GetMapping(value = { "/levelOfDetails/{id}" })
    @Timed
    public ResponseEntity getLevelOfDetail(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = levelOfDetailService.findOne(id, includeFields);    
        else
            responseData = levelOfDetailService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @GetMapping(value = { "/levelOfDetails/" })
    @Timed
    public ResponseEntity getAllLevelsOfDetail( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
            responseData = levelOfDetailService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
}
