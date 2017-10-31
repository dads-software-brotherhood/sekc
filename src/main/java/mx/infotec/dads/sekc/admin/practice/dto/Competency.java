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
    "competency",
    "idCompetency",
    "idCompetencyLevel",
    "competencyLevel"
})
public class Competency {

    @JsonProperty("competency")
    private String competency;
    @JsonProperty("idCompetency")
    private String idCompetency;
    @JsonProperty("idCompetencyLevel")
    private String idCompetencyLevel;
    @JsonProperty("competencyLevel")
    private String competencyLevel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("competency")
    public String getCompetency() {
        return competency;
    }

    @JsonProperty("competency")
    public void setCompetency(String competency) {
        this.competency = competency;
    }

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

    @JsonProperty("competencyLevel")
    public String getCompetencyLevel() {
        return competencyLevel;
    }

    @JsonProperty("competencyLevel")
    public void setCompetencyLevel(String competencyLevel) {
        this.competencyLevel = competencyLevel;
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
