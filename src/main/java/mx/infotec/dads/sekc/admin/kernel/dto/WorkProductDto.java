
package mx.infotec.dads.sekc.admin.kernel.dto;

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
    "levelOfDetail",
    "action",
    "workProductManifest",
    "name",
    "briefDescription",
    "description",
    "suppressable",
    "owner",
    "tag",
    "resource",
    "properties",
    "viewSelection",
    "featureSelection",
    "extension",
    "referrer",
    "patternAssociation"
})
public class WorkProductDto implements BasicElementDto{

    @JsonProperty("levelOfDetail")
    private List<LevelOfDetail> levelOfDetail = null;
    @JsonProperty("action")
    private List<Action> action = null;
    @JsonProperty("workProductManifest")
    private List<WorkProductManifest> workProductManifest = null;
    @JsonProperty("name")
    private String name;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("suppressable")
    private Boolean suppressable;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("tag")
    private List<Tag> tag = null;
    @JsonProperty("resource")
    private List<Resource> resource = null;
    @JsonProperty("properties")
    private List<Property> properties = null;
    @JsonProperty("viewSelection")
    private List<ViewSelection> viewSelection = null;
    @JsonProperty("featureSelection")
    private List<FeatureSelection> featureSelection = null;
    @JsonProperty("extension")
    private List<Extension> extension = null;
    @JsonProperty("referrer")
    private List<Referrer> referrer = null;
    @JsonProperty("patternAssociation")
    private List<PatternAssociation> patternAssociation = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("levelOfDetail")
    public List<LevelOfDetail> getLevelOfDetail() {
        return levelOfDetail;
    }

    @JsonProperty("levelOfDetail")
    public void setLevelOfDetail(List<LevelOfDetail> levelOfDetail) {
        this.levelOfDetail = levelOfDetail;
    }

    @JsonProperty("action")
    public List<Action> getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(List<Action> action) {
        this.action = action;
    }

    @JsonProperty("workProductManifest")
    public List<WorkProductManifest> getWorkProductManifest() {
        return workProductManifest;
    }

    @JsonProperty("workProductManifest")
    public void setWorkProductManifest(List<WorkProductManifest> workProductManifest) {
        this.workProductManifest = workProductManifest;
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

    @JsonProperty("suppressable")
    public Boolean getSuppressable() {
        return suppressable;
    }

    @JsonProperty("suppressable")
    public void setSuppressable(Boolean suppressable) {
        this.suppressable = suppressable;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("tag")
    public List<Tag> getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    @JsonProperty("resource")
    public List<Resource> getResource() {
        return resource;
    }

    @JsonProperty("resource")
    public void setResource(List<Resource> resource) {
        this.resource = resource;
    }

    @JsonProperty("properties")
    public List<Property> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @JsonProperty("viewSelection")
    public List<ViewSelection> getViewSelection() {
        return viewSelection;
    }

    @JsonProperty("viewSelection")
    public void setViewSelection(List<ViewSelection> viewSelection) {
        this.viewSelection = viewSelection;
    }

    @JsonProperty("featureSelection")
    public List<FeatureSelection> getFeatureSelection() {
        return featureSelection;
    }

    @JsonProperty("featureSelection")
    public void setFeatureSelection(List<FeatureSelection> featureSelection) {
        this.featureSelection = featureSelection;
    }

    @JsonProperty("extension")
    public List<Extension> getExtension() {
        return extension;
    }

    @JsonProperty("extension")
    public void setExtension(List<Extension> extension) {
        this.extension = extension;
    }

    @JsonProperty("referrer")
    public List<Referrer> getReferrer() {
        return referrer;
    }

    @JsonProperty("referrer")
    public void setReferrer(List<Referrer> referrer) {
        this.referrer = referrer;
    }

    @JsonProperty("patternAssociation")
    public List<PatternAssociation> getPatternAssociation() {
        return patternAssociation;
    }

    @JsonProperty("patternAssociation")
    public void setPatternAssociation(List<PatternAssociation> patternAssociation) {
        this.patternAssociation = patternAssociation;
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
        return "WorkProductDto{" + "levelOfDetail=" + levelOfDetail + ", action=" + action + ", workProductManifest=" + workProductManifest + ", name=" + name + ", briefDescription=" + briefDescription + ", description=" + description + ", suppressable=" + suppressable + ", owner=" + owner + ", tag=" + tag + ", resource=" + resource + ", properties=" + properties + ", viewSelection=" + viewSelection + ", featureSelection=" + featureSelection + ", extension=" + extension + ", referrer=" + referrer + ", patternAssociation=" + patternAssociation + ", additionalProperties=" + additionalProperties + '}';
    }
    
}
