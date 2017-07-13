package mx.infotec.dads.sekc.admin.practice.rest;

import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

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

import io.swagger.annotations.ApiParam;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class PracticeResource {

    @Autowired
    private PracticeService practiceService;
    
    private static final String ENTITY_NAME = "practices";

    @PostMapping("/practices/")
    public ResponseEntity createPractice(@RequestBody PracticeDto practice) {
        ResponseWrapper responseData;
        responseData = practiceService.save(practice);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, practice.toString()))
                    .body(responseData.getResponseObject());
        }
        /* 
            the response_code can be NotFound, but that can't have a body, 
            so we use badRequest with json error on body
        */
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, practice.toString()))
            .body(responseData.toString());
    }

    @GetMapping(value = { "/practices/", "/practices/{id}" })
    public ResponseEntity practiceGet(@PathVariable(value = "id", required = false) String id,
            @RequestParam(value = "includeFields", required = false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if (id != null)
            responseData = practiceService.findOne(id, includeFields);
        else
            responseData = practiceService.findAll(pageable);
        
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        /* 
            the response_code can be NotFound, but that can't have a body, 
            so we use badRequest with json error on body
        */
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_practices_get", "Error al obtener práctica"))
            .body(responseData.toString());
    }

    @DeleteMapping("/practices/{id}")
    public ResponseEntity practiceDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = practiceService.delete(id);
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_practices_delete"))
                .body(responseData.getResponseObject());
    }
}
