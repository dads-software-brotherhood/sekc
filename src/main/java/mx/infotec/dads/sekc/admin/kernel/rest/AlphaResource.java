package mx.infotec.dads.sekc.admin.kernel.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.kernel.service.AlphaService;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Alpha;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.PathVariable;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import mx.infotec.dads.sekc.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(API_PATH)
public class AlphaResource {

    private final Logger LOGGER = LoggerFactory.getLogger(AlphaResource.class);

    @Autowired
    private AlphaService alphaService;

    private static final String ENTITY_NAME = "alpha";

    @PostMapping("/alphas")
    @Timed
    public ResponseEntity createAlpha(@RequestBody AlphaDto alpha) {
        ResponseWrapper responseData;

        responseData = alphaService.save(alpha);

        if ("".equals(responseData.getErrorMessage())) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, alpha.toString()))
                    .body(responseData.getResponseObject());
        }

        return ResponseEntity.badRequest()
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, alpha.toString()))
                .body(responseData.toString());
    }

    @DeleteMapping("/alphas/{id}")
    @Timed
    public ResponseEntity deleteAlpha(@PathVariable("id") String id) {
        ResponseWrapper responseData = alphaService.delete(id);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "ok_alpha_delete"))
                .body(responseData.getResponseObject());
    }

    @GetMapping(value = {"/alphas/{id}"})
    @Timed
    public ResponseEntity<Alpha> getAlpha(@PathVariable(value = "id", required = true) String id) {

        try {
            Alpha alpha = alphaService.findOne(id);

            if (alpha.getId() != null) {
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                        .body(alpha);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_alpha_get", "Error al obtener Alpha"))
                    .body(alpha);

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        return null;
    }

    /**
     * GET /alphas : get all the alphas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alphas in
     * body
     */
    @GetMapping(value = {"/alphas"})
    @Timed
    public ResponseEntity<List<Alpha>> getAllAlphas(@ApiParam Pageable pageable) {

        LOGGER.debug("REST request to get a page of Alphas");

        Page<Alpha> page = alphaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_PATH + "/alphas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    @GetMapping("/alphas/{id}/workproducts")
    @Timed
    public ResponseEntity getWorkproductsFromAlpha(@PathVariable(value = "id") String id) {

        ResponseWrapper responseData;

        responseData = alphaService.findWorkProductList(id);

        if ("".equals(responseData.getErrorMessage())) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createAlert(ENTITY_NAME, id))
                    .body(responseData.getResponseObject());
        }

        return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_alpha/workproduct_get", "Error al obtener workproduct desde alpha"))
                .body(responseData.toString());
    }

}
