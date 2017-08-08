
package mx.infotec.dads.sekc.admin.practice.consult.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "idAlpha",
    "idState",
    "alpha",
    "state"
})
public class AlphaState {

    @JsonProperty("idAlpha")
    private String idAlpha;
    @JsonProperty("idState")
    private String idState;
    @JsonProperty("alpha")
    private String alpha;
    @JsonProperty("state")
    private String state;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idAlpha")
    public String getIdAlpha() {
        return idAlpha;
    }

    @JsonProperty("idAlpha")
    public void setIdAlpha(String idAlpha) {
        this.idAlpha = idAlpha;
    }

    @JsonProperty("idState")
    public String getIdState() {
        return idState;
    }

    @JsonProperty("idState")
    public void setIdState(String idState) {
        this.idState = idState;
    }

    @JsonProperty("alpha")
    public String getAlpha() {
        return alpha;
    }

    @JsonProperty("alpha")
    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
