package mx.infotec.dads.sekc.admin.catalogs.rest;

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
import mx.infotec.dads.sekc.admin.catalogs.dto.CatalogsDto;
import mx.infotec.dads.sekc.admin.catalogs.service.CatalogsService;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class CatalogsResource {

    @Autowired
    private CatalogsService catalogsService;

    @PostMapping("/catalogss/")
    public ResponseEntity createCatalogs(@RequestBody CatalogsDto catalogs) {
        ResponseWrapper responseData;
        responseData = catalogsService.save(catalogs);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity(responseData.getResponseObject(), responseData.getResponse_code());
        return new ResponseEntity(responseData.toString(), responseData.getResponse_code());

    }

    @GetMapping(value = { "/catalogss/", "/catalogss/{id}" })
    public ResponseEntity catalogsGet(@PathVariable(value = "id", required = false) String id,
            @RequestParam(value = "includeFields", required = false) List<String> includeFields,
            @ApiParam Pageable pageable) {
        ResponseWrapper responseData;
        if (id != null)
            responseData = catalogsService.findOne(id, includeFields);
        else
            responseData = catalogsService.findAll(pageable);

        if (responseData.getError_message().equals(""))
            return new ResponseEntity(responseData.getResponseObject(), responseData.getResponse_code());
        return new ResponseEntity(responseData.toString(), responseData.getResponse_code());
    }

    @DeleteMapping("/catalogss/{id}")
    public ResponseEntity catalogsDelete(@PathVariable("id") String id) {
        ResponseWrapper responseData = catalogsService.delete(id);
        if (responseData.getError_message().equals(""))
            return new ResponseEntity(responseData.getResponseObject(), responseData.getResponse_code());
        return new ResponseEntity(responseData.toString(), responseData.getResponse_code());
    }
}