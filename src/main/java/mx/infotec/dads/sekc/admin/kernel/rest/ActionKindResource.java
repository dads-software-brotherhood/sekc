package mx.infotec.dads.sekc.admin.kernel.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import org.omg.essence.model.activityspaceandactivity.ActionKind;

/**
 *
 * @author wisog
 */
@RestController
@RequestMapping(API_PATH)
public class ActionKindResource {

    private static final String ENTITY_NAME = "actionKind";

    @GetMapping(value = {"/actionKinds"})
    public ResponseEntity getActionKind() {
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert(ENTITY_NAME, ""))
                .body(ActionKind.values());

    }
}
