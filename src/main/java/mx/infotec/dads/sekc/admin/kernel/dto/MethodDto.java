
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
    "purpose",
    "baseKernel",
    "name",
    "description",
    "briefDescription",
    "icon",
    "mergeResolution",
    "ownedElements",
    "referredElements",
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
public class MethodDto implements ElementGroupDto{

    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("baseKernel")
    private BaseKernel baseKernel;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("briefDescription")
    private String briefDescription;
    @JsonProperty("icon")
    private Icon icon;
    @JsonProperty("mergeResolution")
    private List<MergeResolution> mergeResolution = null;
    @JsonProperty("ownedElements")
    private List<OwnedElement> ownedElements = null;
    @JsonProperty("referredElements")
    private List<ReferredElement> referredElements = null;
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

    @JsonProperty("purpose")
    public String getPurpose() {
        return purpose;
    }

    @JsonProperty("purpose")
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @JsonProperty("baseKernel")
    public BaseKernel getBaseKernel() {
        return baseKernel;
    }

    @JsonProperty("baseKernel")
    public void setBaseKernel(BaseKernel baseKernel) {
        this.baseKernel = baseKernel;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("briefDescription")
    public String getBriefDescription() {
        return briefDescription;
    }

    @JsonProperty("briefDescription")
    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    @JsonProperty("icon")
    public Icon getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    @JsonProperty("mergeResolution")
    public List<MergeResolution> getMergeResolution() {
        return mergeResolution;
    }

    @JsonProperty("mergeResolution")
    public void setMergeResolution(List<MergeResolution> mergeResolution) {
        this.mergeResolution = mergeResolution;
    }

    @JsonProperty("ownedElements")
    public List<OwnedElement> getOwnedElements() {
        return ownedElements;
    }

    @JsonProperty("ownedElements")
    public void setOwnedElements(List<OwnedElement> ownedElements) {
        this.ownedElements = ownedElements;
    }

    @JsonProperty("referredElements")
    public List<ReferredElement> getReferredElements() {
        return referredElements;
    }

    @JsonProperty("referredElements")
    public void setReferredElements(List<ReferredElement> referredElements) {
        this.referredElements = referredElements;
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
        return "MethodDto{" + "purpose=" + purpose + ", baseKernel=" + baseKernel + ", name=" + name + ", description=" + description + ", briefDescription=" + briefDescription + ", icon=" + icon + ", mergeResolution=" + mergeResolution + ", ownedElements=" + ownedElements + ", referredElements=" + referredElements + ", suppressable=" + suppressable + ", owner=" + owner + ", tag=" + tag + ", resource=" + resource + ", properties=" + properties + ", viewSelection=" + viewSelection + ", featureSelection=" + featureSelection + ", extension=" + extension + ", referrer=" + referrer + ", patternAssociation=" + patternAssociation + ", additionalProperties=" + additionalProperties + '}';
    }

}
