
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
    "competencyLevels"
})
public class Competency {

    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("competencyLevels")
    private List<CompetencyLevel> competencyLevels = null;

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

    @JsonProperty("competencyLevels")
    public List<CompetencyLevel> getCompetencyLevels() {
        return competencyLevels;
    }

    @JsonProperty("competencyLevels")
    public void setCompetencyLevels(List<CompetencyLevel> competencyLevels) {
        this.competencyLevels = competencyLevels;
    }

}
