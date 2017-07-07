package mx.infotec.dads.sekc.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.sekc.web.rest.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;

/**
 * Rest Service to CRUD Practices
 *
 * @author alejandro.aguayo
 */
@RestController
@RequestMapping(API_PATH)
public class PracticesRestService {

    @Autowired
    private SEPracticeRepository practiceRepository;

    private final Logger LOG = LoggerFactory.getLogger(PracticesRestService.class);

    /*
     * Create Practice, Method: POST
     */
    @PostMapping("/practice")
    public ResponseEntity createPractice(@RequestBody Object practice) {

        Map<String, Object> practiceMap;
        SEPractice practiceToPersiste = new SEPractice();

        /*
         * Try to convert the JSON sended from the FRONT into a
         * Map<String,Object>, get all the required fields and do the
         * persistence of this new Practice. Any Exception will send a
         * BAD_REQUEST to the FRONT. If everything is correct, send a OK status
         * to the FRONT.
         */
        try {
            practiceMap = (Map<String, Object>) practice;

            practiceToPersiste.setConsistencyRules((String) practiceMap.get("consistencyRules"));
            practiceToPersiste.setEntry((List<String>) practiceMap.get("entry"));
            practiceToPersiste.setMeasures((List<String>) practiceMap.get("measures"));
            practiceToPersiste.setObjective((String) practiceMap.get("objective"));
            practiceToPersiste.setResult((List<String>) practiceMap.get("result"));
            practiceToPersiste.setBriefDescription((String) practiceMap.get("briefDescription"));
            practiceToPersiste.setDescription((String) practiceMap.get("description"));
            practiceToPersiste.setName((String) practiceMap.get("name"));

            // After the implementation of the "essence-impl" project, it is a
            // MUST to create a function
            // to check the Data in the object, to prevent to persiste Practices
            // with Null values in required FIELDS
            // if (ValidationsController.checkValid(practiceToPersiste) ){
            // log.debug("Some of the required FIELDS are NULL, it can't be
            // persisted");
            // return new ResponseEntity( HttpStatus.BAD_REQUEST );
            // }
            practiceRepository.save(practiceToPersiste);
            // After the implementation of the "essence-impl" project, in this
            // lines we're going to save the new Practice
            return new ResponseEntity(practiceToPersiste, HttpStatus.OK);

        } catch (Exception e) {
            LOG.debug("Fail to obtain the needed FIELDS ", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/practice")
    public ResponseEntity practiceGet(@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "includeFields", required = false) List<String> includeFields) {

        Object regresar;
        if (id != null) {
            regresar = practiceRepository.findOne(id);
            // At this point, we can clean & validate the info before response
            if (includeFields != null && regresar != null)
                return new ResponseEntity(RandomUtil.filterResponseFields(regresar, includeFields), HttpStatus.OK);
        } else {
            regresar = practiceRepository.findAll();
        }

        return new ResponseEntity(regresar, HttpStatus.OK);
    }

    @DeleteMapping("/practice")
    public ResponseEntity practiceDelete(@RequestParam("id") String id) {
        practiceRepository.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}