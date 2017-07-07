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
import mx.infotec.dads.sekc.admin.kernel.service.StateService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(API_PATH)
public class StateResource {

    @Autowired
    private StateService stateService;

    @PostMapping("/states/")
    public ResponseEntity StateCreate(@RequestBody Object state) {
        ResponseWrapper responseData;
        responseData = stateService.save(state);
        
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }

    @GetMapping(value = { "/states/","/states/{id}" })
    public ResponseEntity stateGet(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = stateService.findOne(id, includeFields);    
        else
            responseData = stateService.findAll(pageable);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
    
    @DeleteMapping("/states/{id}")
    public ResponseEntity stateDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = stateService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponse_code() );
        return new ResponseEntity( responseData.toString(), responseData.getResponse_code() );
    }
}