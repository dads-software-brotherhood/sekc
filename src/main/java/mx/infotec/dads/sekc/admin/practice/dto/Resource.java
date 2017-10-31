package mx.infotec.dads.sekc.admin.practice.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "content",
    "idTypeResource",
    "file",
    "fileName",
    "fileType"
})
public class Resource {

    @JsonProperty("content")
    private String content;
    @JsonProperty("idTypeResource")
    private String idTypeResource;
    @JsonProperty("file")
    private String file;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("fileType")
    private String fileType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("idTypeResource")
    public String getIdTypeResource() {
        return idTypeResource;
    }

    @JsonProperty("idTypeResource")
    public void setIdTypeResource(String idTypeResource) {
        this.idTypeResource = idTypeResource;
    }

    @JsonProperty("file")
    public String getFile() {
        return file;
    }

    @JsonProperty("file")
    public void setFile(String file) {
        this.file = file;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty("fileType")
    public String getFileType() {
        return fileType;
    }

    @JsonProperty("fileType")
    public void setFileType(String fileType) {
        this.fileType = fileType;
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
