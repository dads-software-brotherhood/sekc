
package mx.infotec.dads.sekc.admin.practice.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "alphaStates", "workProductsLevelofdetail", "otherConditions" })
public class CompletitionCriterion {

    @JsonProperty("alphaStates")
    private List<AlphaState> alphaStates = null;
    @JsonProperty("workProductsLevelofdetail")
    private List<WorkProductsLevelofdetail> workProductsLevelofdetail = null;
    @JsonProperty("otherConditions")
    private List<String> otherConditions = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("alphaStates")
    public List<AlphaState> getAlphaStates() {
        return alphaStates;
    }

    @JsonProperty("alphaStates")
    public void setAlphaStates(List<AlphaState> alphaStates) {
        this.alphaStates = alphaStates;
    }

    @JsonProperty("workProductsLevelofdetail")
    public List<WorkProductsLevelofdetail> getWorkProductsLevelofdetail() {
        return workProductsLevelofdetail;
    }

    @JsonProperty("workProductsLevelofdetail")
    public void setWorkProductsLevelofdetail(List<WorkProductsLevelofdetail> workProductsLevelofdetail) {
        this.workProductsLevelofdetail = workProductsLevelofdetail;
    }

    @JsonProperty("otherConditions")
    public List<String> getOtherConditions() {
        return otherConditions;
    }

    @JsonProperty("otherConditions")
    public void setOtherConditions(List<String> otherConditions) {
        this.otherConditions = otherConditions;
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
