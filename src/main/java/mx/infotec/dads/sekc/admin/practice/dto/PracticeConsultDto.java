
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

/**
 * PracticeConsultDto
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idPractice", "name", "objective", "briefDesciption", "description", "author", "keywords", "lastModifiedDate",})
public class PracticeConsultDto {

    @JsonProperty("idPractice")
    private String idPractice;
    @JsonProperty("name")
    private String name;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("briefDescription")
    private String briefDesciption;
    @JsonProperty("description")
    private String description;
    @JsonProperty("author")
    private String author;
    @JsonProperty("keywords")
    private List<String> keywords = null;
    @JsonProperty("lastModifiedDate")
    private String lastModifiedDate;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idPractice")
    public String getIdPractice() {
        return idPractice;
    }

    @JsonProperty("idPractice")
    public void setIdPractice(String idPractice) {
        this.idPractice = idPractice;
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

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
