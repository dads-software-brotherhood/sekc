
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
    "idAlpha",
    "subAlphas",
    "workProducts"
})
public class AlphasSelection {

    @JsonProperty("idAlpha")
    private String idAlpha;
    @JsonProperty("subAlphas")
    private List<String> subAlphas = null;
    @JsonProperty("workProducts")
    private List<String> workProducts = null;
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

    @JsonProperty("subAlphas")
    public List<String> getSubAlphas() {
        return subAlphas;
    }

    @JsonProperty("subAlphas")
    public void setSubAlphas(List<String> subAlphas) {
        this.subAlphas = subAlphas;
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
