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
    "description",
    "idWorkProduct",
    "idLevelOfDetail"
})
public class WorkProductsLevelofDetail {

    @JsonProperty("description")
    private String description;
    @JsonProperty("idWorkProduct")
    private String idWorkProduct;
    @JsonProperty("idLevelOfDetail")
    private String idLevelOfDetail;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("idWorkProduct")
    public String getIdWorkProduct() {
        return idWorkProduct;
    }

    @JsonProperty("idWorkProduct")
    public void setIdWorkProduct(String idWorkProduct) {
        this.idWorkProduct = idWorkProduct;
    }

    @JsonProperty("idLevelOfDetail")
    public String getIdLevelOfDetail() {
        return idLevelOfDetail;
    }

    @JsonProperty("idLevelOfDetail")
    public void setIdLevelOfDetail(String idLevelOfDetail) {
        this.idLevelOfDetail = idLevelOfDetail;
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
