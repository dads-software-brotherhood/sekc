
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
    "color",
    "alphas",
    "activitySpaces",
    "competencies"
})
public class AreasOfConcern {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("color")
    private String color;
    @JsonProperty("alphas")
    private List<Alpha> alphas = null;
    @JsonProperty("activitySpaces")
    private List<ActivitySpace> activitySpaces = null;
    @JsonProperty("competencies")
    private List<Competency> competencies = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("type")
    private String type = "areaOfConcern";
    
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
    
    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("alphas")
    public List<Alpha> getAlphas() {
        return alphas;
    }

    @JsonProperty("alphas")
    public void setAlphas(List<Alpha> alphas) {
        this.alphas = alphas;
    }

    @JsonProperty("activitySpaces")
    public List<ActivitySpace> getActivitySpaces() {
        return activitySpaces;
    }

    @JsonProperty("activitySpaces")
    public void setActivitySpaces(List<ActivitySpace> activitySpaces) {
        this.activitySpaces = activitySpaces;
    }

    @JsonProperty("competencies")
    public List<Competency> getCompetencies() {
        return competencies;
    }

    @JsonProperty("competencies")
    public void setCompetencies(List<Competency> competencies) {
        this.competencies = competencies;
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
