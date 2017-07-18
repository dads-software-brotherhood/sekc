
package mx.infotec.dads.sekc.config.dbmigrations.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "briefDescription",
    "description",
    "alphas",
    "activitySpaces",
    "competencies"
})
public class AreasOfConcern {

    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("alphas")
    private List<Alpha> alphas = null;
    @JsonProperty("activitySpaces")
    private List<ActivitySpace> activitySpaces = null;
    @JsonProperty("competencies")
    private List<Competency> competencies = null;

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

}
