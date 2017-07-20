
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
    "idKernel",
    "name",
    "objective",
    "briefDesciption",
    "description",
    "consistencyRules",
    "relatedPractices",
    "author",
    "keywords",
    "conditions",
    "thingsToWorkWith",
    "thingsToDo"
})
public class PracticeDto {

    @JsonProperty("idKernel")
    private String idKernel;
    @JsonProperty("name")
    private String name;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("briefDesciption")
    private String briefDesciption;
    @JsonProperty("description")
    private String description;
    @JsonProperty("consistencyRules")
    private String consistencyRules;
    @JsonProperty("relatedPractices")
    private List<String> relatedPractices = null;
    @JsonProperty("author")
    private String author;
    @JsonProperty("keywords")
    private List<String> keywords = null;
    @JsonProperty("conditions")
    private Conditions conditions;
    @JsonProperty("thingsToWorkWith")
    private ThingsToWorkWith thingsToWorkWith;
    @JsonProperty("thingsToDo")
    private ThingsToDo thingsToDo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idKernel")
    public String getIdKernel() {
        return idKernel;
    }

    @JsonProperty("idKernel")
    public void setIdKernel(String idKernel) {
        this.idKernel = idKernel;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("objective")
    public String getObjective() {
        return objective;
    }

    @JsonProperty("objective")
    public void setObjective(String objective) {
        this.objective = objective;
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

    @JsonProperty("consistencyRules")
    public String getConsistencyRules() {
        return consistencyRules;
    }

    @JsonProperty("consistencyRules")
    public void setConsistencyRules(String consistencyRules) {
        this.consistencyRules = consistencyRules;
    }

    @JsonProperty("relatedPractices")
    public List<String> getRelatedPractices() {
        return relatedPractices;
    }

    @JsonProperty("relatedPractices")
    public void setRelatedPractices(List<String> relatedPractices) {
        this.relatedPractices = relatedPractices;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("keywords")
    public List<String> getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("conditions")
    public Conditions getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
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
    
    @Override
    public String toString() {
        return "PracticeDto{" + "idKernel=" + idKernel + ", name=" + name + ", objective=" + objective + ", briefDesciption=" + briefDesciption + ", description=" + description + ", consistencyRules=" + consistencyRules + ", relatedPractices=" + relatedPractices + ", author=" + author + ", keywords=" + keywords + ", conditions=" + conditions + ", thingsToWorkWith=" + thingsToWorkWith + ", thingsToDo=" + thingsToDo + ", additionalProperties=" + additionalProperties + '}';
    }

}
