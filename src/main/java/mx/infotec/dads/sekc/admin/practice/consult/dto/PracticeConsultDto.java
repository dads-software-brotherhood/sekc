
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
    "briefDescription",
    "description",
    "consistencyRules",
    "objective",
    "measures",
    "keyWords",
    "author",
    "entries",
    "results",
    "thingsToWorkWith",
    "thingsToDo"
})
public class PracticeConsultDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("consistencyRules")
    private String consistencyRules;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("measures")
    private List<String> measures = null;
    @JsonProperty("keyWords")
    private List<String> keyWords = null;
    @JsonProperty("author")
    private String author;
    @JsonProperty("entries")
    private List<Entry> entries = null;
    @JsonProperty("results")
    private List<Result> results = null;
    @JsonProperty("thingsToWorkWith")
    private ThingsToWorkWith thingsToWorkWith;
    @JsonProperty("thingsToDo")
    private ThingsToDo thingsToDo;
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

    @JsonProperty("consistencyRules")
    public String getConsistencyRules() {
        return consistencyRules;
    }

    @JsonProperty("consistencyRules")
    public void setConsistencyRules(String consistencyRules) {
        this.consistencyRules = consistencyRules;
    }

    @JsonProperty("objective")
    public String getObjective() {
        return objective;
    }

    @JsonProperty("objective")
    public void setObjective(String objective) {
        this.objective = objective;
    }

    @JsonProperty("measures")
    public List<String> getMeasures() {
        return measures;
    }

    @JsonProperty("measures")
    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    @JsonProperty("keyWords")
    public List<String> getKeyWords() {
        return keyWords;
    }

    @JsonProperty("keyWords")
    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("entries")
    public List<Entry> getEntries() {
        return entries;
    }

    @JsonProperty("entries")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonProperty("thingsToWorkWith")
    public ThingsToWorkWith getThingsToWorkWith() {
        return thingsToWorkWith;
    }

    @JsonProperty("thingsToWorkWith")
    public void setThingsToWorkWith(ThingsToWorkWith thingsToWorkWith) {
        this.thingsToWorkWith = thingsToWorkWith;
    }

    @JsonProperty("thingsToDo")
    public ThingsToDo getThingsToDo() {
        return thingsToDo;
    }

    @JsonProperty("thingsToDo")
    public void setThingsToDo(ThingsToDo thingsToDo) {
        this.thingsToDo = thingsToDo;
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
