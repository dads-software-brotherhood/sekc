package mx.infotec.dads.sekc.admin.kernel.rest;

import io.swagger.annotations.ApiParam;
import java.util.List;
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
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(API_PATH)
public class CheckPointResource {

    @Autowired
    private CheckPointService checkPointService;

    @PostMapping("/checkpoints/")
    public ResponseEntity checkPointCreate(@RequestBody Object checkPoint) {
        ResponseWrapper responseData;
        responseData = checkPointService.save(checkPoint);
        
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }

    @GetMapping(value = { "/checkpoints/","/checkpoints/{id}" })
    public ResponseEntity checkPointGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = checkPointService.findOne(id, includeFields);    
        else
            responseData = checkPointService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/checkpoints/{id}")
    public ResponseEntity checkPointDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = checkPointService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
}