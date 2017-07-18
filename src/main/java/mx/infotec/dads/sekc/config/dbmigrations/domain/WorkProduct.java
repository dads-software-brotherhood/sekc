
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
    "levelOfDetails"
})
public class WorkProduct {

    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("levelOfDetails")
    private List<LevelOfDetail> levelOfDetails = null;

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

    @JsonProperty("levelOfDetails")
    public List<LevelOfDetail> getLevelOfDetails() {
        return levelOfDetails;
    }

    @JsonProperty("levelOfDetails")
    public void setLevelOfDetails(List<LevelOfDetail> levelOfDetails) {
        this.levelOfDetails = levelOfDetails;
    }

}
