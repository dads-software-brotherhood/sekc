package mx.infotec.dads.sekc.admin.practice.catalog.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.catalog.service.CatalogsService;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class CatalogsResource {

    @Autowired
    private CatalogsService catalogsService;

    private static final String ENTITY_NAME = "catalogs";

    @GetMapping(value = {"/catalogs"})
    public ResponseEntity catalogsGet() {
        ResponseWrapper responseData;
        responseData = catalogsService.findAll();

        if (responseData.getError_message().equals("")) {
            return ResponseEntity.ok()
                    .body(responseData.getResponseObject());
        }
        return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "err_catalogs", "Error en consulta de catalogos"))
                .body(responseData.toString());
    }
}
