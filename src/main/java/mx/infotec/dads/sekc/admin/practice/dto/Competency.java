
package mx.infotec.dads.sekc.admin.practice.dto;

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
    "idCompetency",
    "idCompetencyLevel"
})
public class Competency {

    @JsonProperty("idCompetency")
    private String idCompetency;
    @JsonProperty("idCompetencyLevel")
    private String idCompetencyLevel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idCompetency")
    public String getIdCompetency() {
        return idCompetency;
    }

    @JsonProperty("idCompetency")
    public void setIdCompetency(String idCompetency) {
        this.idCompetency = idCompetency;
    }

    @JsonProperty("idCompetencyLevel")
    public String getIdCompetencyLevel() {
        return idCompetencyLevel;
    }

    @JsonProperty("idCompetencyLevel")
    public void setIdCompetencyLevel(String idCompetencyLevel) {
        this.idCompetencyLevel = idCompetencyLevel;
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
