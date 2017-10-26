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
import mx.infotec.dads.sekc.admin.kernel.service.LibraryService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 *
 * @author wisog
 */

@RestController
@RequestMapping(API_PATH)
public class LibraryResource {
    
    @Autowired
    private LibraryService libraryService;
    
    @PostMapping("/libraries/")
    @Timed
    public ResponseEntity createLibrary( @RequestBody Object library ){
        ResponseWrapper responseData;
        
        responseData = libraryService.save(library);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @DeleteMapping("/libraries/{id}")
    @Timed
    public ResponseEntity deleteLibrary(@PathVariable("id") String id) {
        ResponseWrapper responseData = libraryService.delete(id);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }

    @GetMapping(value = { "/libraries/{id}" })
    @Timed
    public ResponseEntity getLibrary(@PathVariable(value="id", required=false) String id, 
            @RequestParam (value="includeFields", required=false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if ( id != null)
            responseData = libraryService.findOne(id, includeFields);    
        else
            responseData = libraryService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
    @GetMapping(value = { "/libraries/" })
    @Timed
    public ResponseEntity getAllLibraries( @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        
            responseData = libraryService.findAll(pageable);
        if (responseData.getErrorMessage().equals(""))
            return new ResponseEntity( responseData.getResponseObject(), responseData.getResponseCode() );
        return new ResponseEntity( responseData.toString(), responseData.getResponseCode() );
    }
    
}
