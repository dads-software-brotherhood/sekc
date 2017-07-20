
package mx.infotec.dads.sekc.admin.kernel.dto;

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
    "type",
    "idAlphaContainment"
})
public class AlphaContainment {

    @JsonProperty("type")
    private String type;
    @JsonProperty("idAlphaContainment")
    private String idAlphaContainment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("idAlphaContainment")
    public String getIdAlphaContainment() {
        return idAlphaContainment;
    }

    @JsonProperty("idAlphaContainment")
    public void setIdAlphaContainment(String idAlphaContainment) {
        this.idAlphaContainment = idAlphaContainment;
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
