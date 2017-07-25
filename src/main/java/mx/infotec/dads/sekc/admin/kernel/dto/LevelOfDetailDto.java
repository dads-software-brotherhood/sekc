
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
    "name",
    "description",
    "isSufficientLevel",
    "checkListItem",
    "successor",
    "criterion",
    "predecessor",
    "workProduct",
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
public class LevelOfDetailDto implements LanguageElementDto{

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isSufficientLevel")
    private Boolean isSufficientLevel;
    @JsonProperty("checkListItem")
    private List<CheckListItem> checkListItem = null;
    @JsonProperty("successor")
    private Successor successor;
    @JsonProperty("criterion")
    private List<Criterion> criterion = null;
    @JsonProperty("predecessor")
    private Predecessor predecessor;
    @JsonProperty("workProduct")
    private WorkProduct workProduct;
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

    @JsonProperty("isSufficientLevel")
    public Boolean getIsSufficientLevel() {
        return isSufficientLevel;
    }

    @JsonProperty("isSufficientLevel")
    public void setIsSufficientLevel(Boolean isSufficientLevel) {
        this.isSufficientLevel = isSufficientLevel;
    }

    @JsonProperty("checkListItem")
    public List<CheckListItem> getCheckListItem() {
        return checkListItem;
    }

    @JsonProperty("checkListItem")
    public void setCheckListItem(List<CheckListItem> checkListItem) {
        this.checkListItem = checkListItem;
    }

    @JsonProperty("successor")
    public Successor getSuccessor() {
        return successor;
    }

    @JsonProperty("successor")
    public void setSuccessor(Successor successor) {
        this.successor = successor;
    }

    @JsonProperty("criterion")
    public List<Criterion> getCriterion() {
        return criterion;
    }

    @JsonProperty("criterion")
    public void setCriterion(List<Criterion> criterion) {
        this.criterion = criterion;
    }

    @JsonProperty("predecessor")
    public Predecessor getPredecessor() {
        return predecessor;
    }

    @JsonProperty("predecessor")
    public void setPredecessor(Predecessor predecessor) {
        this.predecessor = predecessor;
    }

    @JsonProperty("workProduct")
    public WorkProduct getWorkProduct() {
        return workProduct;
    }

    @JsonProperty("workProduct")
    public void setWorkProduct(WorkProduct workProduct) {
        this.workProduct = workProduct;
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
        return "LevelOfDetailDto{" + "name=" + name + ", description=" + description + ", isSufficientLevel=" + isSufficientLevel + ", checkListItem=" + checkListItem + ", successor=" + successor + ", criterion=" + criterion + ", predecessor=" + predecessor + ", workProduct=" + workProduct + ", suppressable=" + suppressable + ", owner=" + owner + ", tag=" + tag + ", resource=" + resource + ", properties=" + properties + ", viewSelection=" + viewSelection + ", featureSelection=" + featureSelection + ", extension=" + extension + ", referrer=" + referrer + ", patternAssociation=" + patternAssociation + ", additionalProperties=" + additionalProperties + '}';
    }

}
