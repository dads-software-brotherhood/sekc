package mx.infotec.dads.sekc.admin.practice.rest;

import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiParam;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeConsultDto;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import mx.infotec.dads.sekc.web.rest.util.PaginationUtil;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class PracticeResource {
    
    private final Logger log = LoggerFactory.getLogger(PracticeResource.class);

    @Autowired
    private PracticeService practiceService;
    
    private static final String ENTITY_NAME = "practices";

    @PostMapping("/practices")
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

    @GetMapping(value = { "/practices/{id}" })
    public ResponseEntity practiceGet(@PathVariable(value = "id", required = true) String id,
            @RequestParam(value = "includeFields", required = false) List<String> includeFields) {
        ResponseWrapper responseData;
        responseData = practiceService.findOne(id, includeFields);
        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }
        /*
         * the response_code can be NotFound, but that can't have a body, so we
         * use badRequest with json error on body
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
    
    /**
     * GET /practices : get all the practices.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of practices
     *         in body
     */
    @GetMapping("/practices")
    @Timed
    public ResponseEntity<List<PracticeConsultDto>> getAllPractices(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Practice");
        Page<PracticeConsultDto> page = practiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/v1/practices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
