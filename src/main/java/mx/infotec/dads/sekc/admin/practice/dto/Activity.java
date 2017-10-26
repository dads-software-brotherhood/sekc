
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
    "idActivity",
    "idActivitySpace",
    "name",
    "briefDesciption",
    "description",
    "to",
    "goJsPosition",
    "created",
    "competencies",
    "approaches",
    "actions",
    "entryCriterion",
    "completitionCriterion",
    "resources"
})
public class Activity {

    @JsonProperty("idActivity")
    private String idActivity;
    @JsonProperty("idActivitySpace")
    private String idActivitySpace;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDesciption")
    private String briefDesciption;
    @JsonProperty("description")
    private String description;
    @JsonProperty("to")
    private List<String> to = null;
    @JsonProperty("goJsPosition")
    private String goJsPosition;
    @JsonProperty("created")
    private Boolean created;
    @JsonProperty("competencies")
    private List<Competency> competencies = null;
    @JsonProperty("approaches")
    private List<Approach> approaches = null;
    @JsonProperty("actions")
    private List<Action> actions = null;
    @JsonProperty("entryCriterion")
    private EntryCriterion entryCriterion;
    @JsonProperty("completitionCriterion")
    private CompletitionCriterion completitionCriterion;
    @JsonProperty("resources")
    private List<Resource> resources = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idActivity")
    public String getIdActivity() {
        return idActivity;
    }

    @JsonProperty("idActivity")
    public void setIdActivity(String idActivity) {
        this.idActivity = idActivity;
    }

    @JsonProperty("idActivitySpace")
    public String getIdActivitySpace() {
        return idActivitySpace;
    }

    @JsonProperty("idActivitySpace")
    public void setIdActivitySpace(String idActivitySpace) {
        this.idActivitySpace = idActivitySpace;
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

    @JsonProperty("to")
    public List<String> getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(List<String> to) {
        this.to = to;
    }

    @JsonProperty("goJsPosition")
    public String getGoJsPosition() {
        return goJsPosition;
    }

    @JsonProperty("goJsPosition")
    public void setGoJsPosition(String goJsPosition) {
        this.goJsPosition = goJsPosition;
    }

    @JsonProperty("created")
    public Boolean getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Boolean created) {
        this.created = created;
    }

    @JsonProperty("competencies")
    public List<Competency> getCompetencies() {
        return competencies;
    }

    @JsonProperty("competencies")
    public void setCompetencies(List<Competency> competencies) {
        this.competencies = competencies;
    }

    @JsonProperty("approaches")
    public List<Approach> getApproaches() {
        return approaches;
    }

    @JsonProperty("approaches")
    public void setApproaches(List<Approach> approaches) {
        this.approaches = approaches;
    }

    @JsonProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    @JsonProperty("actions")
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @JsonProperty("entryCriterion")
    public EntryCriterion getEntryCriterion() {
        return entryCriterion;
    }

    @JsonProperty("entryCriterion")
    public void setEntryCriterion(EntryCriterion entryCriterion) {
        this.entryCriterion = entryCriterion;
    }

    @JsonProperty("completitionCriterion")
    public CompletitionCriterion getCompletitionCriterion() {
        return completitionCriterion;
    }

    @JsonProperty("completitionCriterion")
    public void setCompletitionCriterion(CompletitionCriterion completitionCriterion) {
        this.completitionCriterion = completitionCriterion;
    }

    @JsonProperty("resources")
    public List<Resource> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<Resource> resources) {
        this.resources = resources;
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
