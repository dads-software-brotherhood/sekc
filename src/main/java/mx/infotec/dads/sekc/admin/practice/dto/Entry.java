
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
@JsonPropertyOrder({
    "alphaStates",
    "workProductsLevelofDetail",
    "otherConditions"
})
public class Entry {

    @JsonProperty("alphaStates")
    private List<AlphaState> alphaStates = null;
    @JsonProperty("workProductsLevelofDetail")
    private List<WorkProductsLevelofDetail> workProductsLevelofDetail = null;
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

    @JsonProperty("workProductsLevelofDetail")
    public List<WorkProductsLevelofDetail> getWorkProductsLevelofDetail() {
        return workProductsLevelofDetail;
    }

    @JsonProperty("workProductsLevelofDetail")
    public void setWorkProductsLevelofDetail(List<WorkProductsLevelofDetail> workProductsLevelofDetail) {
        this.workProductsLevelofDetail = workProductsLevelofDetail;
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
