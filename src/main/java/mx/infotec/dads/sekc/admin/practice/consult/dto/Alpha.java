
package mx.infotec.dads.sekc.admin.practice.consult.dto;

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
    "id",
    "name",
    "briefDesciption",
    "description",
    "states",
    "workProducts"
})
public class Alpha {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDesciption")
    private String briefDesciption;
    @JsonProperty("description")
    private String description;
    @JsonProperty("states")
    private List<State> states = null;
    @JsonProperty("workProducts")
    private List<WorkProduct> workProducts = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("briefDesciption")
    public String getBriefDesciption() {
        return briefDesciption;
    }

    @JsonProperty("briefDesciption")
    public void setBriefDesciption(String briefDesciption) {
        this.briefDesciption = briefDesciption;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("states")
    public List<State> getStates() {
        return states;
    }

    @JsonProperty("states")
    public void setStates(List<State> states) {
        this.states = states;
    }

    @JsonProperty("workProducts")
    public List<WorkProduct> getWorkProducts() {
        return workProducts;
    }

    @JsonProperty("workProducts")
    public void setWorkProducts(List<WorkProduct> workProducts) {
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
