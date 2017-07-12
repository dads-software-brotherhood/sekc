
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
    "kernels",
    "alphas",
    "activitySpaces",
    "practices",
    "competencies",
    "actionsKinds",
    "resourcesTypes"
})
public class Catalogs {

    @JsonProperty("kernels")
    private List<Kernel> kernels = null;
    @JsonProperty("alphas")
    private List<Alpha> alphas = null;
    @JsonProperty("activitySpaces")
    private List<ActivitySpace> activitySpaces = null;
    @JsonProperty("practices")
    private List<Practice> practices = null;
    @JsonProperty("competencies")
    private List<CompetencyCatalog> competencies = null;
    @JsonProperty("actionsKinds")
    private List<ActionsKind> actionsKinds = null;
    @JsonProperty("resourcesTypes")
    private List<ResourcesType> resourcesTypes = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("kernels")
    public List<Kernel> getKernels() {
        return kernels;
    }

    @JsonProperty("kernels")
    public void setKernels(List<Kernel> kernels) {
        this.kernels = kernels;
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

    @JsonProperty("practices")
    public List<Practice> getPractices() {
        return practices;
    }

    @JsonProperty("practices")
    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }

    @JsonProperty("competencies")
    public List<CompetencyCatalog> getCompetencies() {
        return competencies;
    }

    @JsonProperty("competencies")
    public void setCompetencies(List<CompetencyCatalog> competencies) {
        this.competencies = competencies;
    }

    @JsonProperty("actionsKinds")
    public List<ActionsKind> getActionsKinds() {
        return actionsKinds;
    }

    @JsonProperty("actionsKinds")
    public void setActionsKinds(List<ActionsKind> actionsKinds) {
        this.actionsKinds = actionsKinds;
    }

    @JsonProperty("resourcesTypes")
    public List<ResourcesType> getResourcesTypes() {
        return resourcesTypes;
    }

    @JsonProperty("resourcesTypes")
    public void setResourcesTypes(List<ResourcesType> resourcesTypes) {
        this.resourcesTypes = resourcesTypes;
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
