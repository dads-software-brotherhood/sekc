
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
    "alphas",
    "workProducts"
})
public class ThingsToWorkWith {

    @JsonProperty("alphas")
    private List<String> alphas = null;
    @JsonProperty("workProducts")
    private List<String> workProducts = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("alphas")
    public List<String> getAlphas() {
        return alphas;
    }

    @JsonProperty("alphas")
    public void setAlphas(List<String> alphas) {
        this.alphas = alphas;
    }

    @JsonProperty("workProducts")
    public List<String> getWorkProducts() {
        return workProducts;
    }

    @JsonProperty("workProducts")
    public void setWorkProducts(List<String> workProducts) {
        this.workProducts = workProducts;
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
