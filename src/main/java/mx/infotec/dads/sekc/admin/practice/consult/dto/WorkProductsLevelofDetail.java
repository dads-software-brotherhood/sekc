
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
    "idWorkProduct",
    "idLevelOfDetail",
    "workProduct",
    "levelOfDetail"
})
public class WorkProductsLevelofDetail {

    @JsonProperty("idWorkProduct")
    private String idWorkProduct;
    @JsonProperty("idLevelOfDetail")
    private String idLevelOfDetail;
    @JsonProperty("workProduct")
    private String workProduct;
    @JsonProperty("levelOfDetail")
    private String levelOfDetail;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("workProduct")
    public String getWorkProduct() {
        return workProduct;
    }

    @JsonProperty("workProduct")
    public void setWorkProduct(String workProduct) {
        this.workProduct = workProduct;
    }

    @JsonProperty("levelOfDetail")
    public String getLevelOfDetail() {
        return levelOfDetail;
    }

    @JsonProperty("levelOfDetail")
    public void setLevelOfDetail(String levelOfDetail) {
        this.levelOfDetail = levelOfDetail;
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
