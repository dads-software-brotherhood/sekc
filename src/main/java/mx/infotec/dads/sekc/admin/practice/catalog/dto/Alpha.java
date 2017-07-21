
package mx.infotec.dads.sekc.admin.practice.catalog.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "briefDescription",
    "description",
    "states",
    "workproducts"
})
public class Alpha {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("states")
    private List<State> states = null;
    @JsonProperty("workproducts")
    private List<Workproduct> workproducts = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("type")
    private String type = "alpha";
    
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

    @JsonProperty("briefDescription")
    public String getBriefDescription() {
        return briefDescription;
    }

    @JsonProperty("briefDescription")
    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
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

    @JsonProperty("workproducts")
    public List<Workproduct> getWorkproducts() {
        return workproducts;
    }

    @JsonProperty("workproducts")
    public void setWorkproducts(List<Workproduct> workproducts) {
        this.workproducts = workproducts;
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
