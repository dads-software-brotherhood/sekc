package mx.infotec.dads.sekc.admin.kernel.dto;

import java.util.List;

/**
 *
 * @author wisog
 */
public interface LanguageElementDto {
    
    public Boolean getSuppressable();

    public void setSuppressable(Boolean suppressable);

    public Owner getOwner();

    public void setOwner(Owner owner);

    public List<Tag> getTag();

    public void setTag(List<Tag> tag);

    public List<Resource> getResource();

    public void setResource(List<Resource> resource);

    public List<Property> getProperties();

    public void setProperties(List<Property> properties);

    public List<ViewSelection> getViewSelection();

    public void setViewSelection(List<ViewSelection> viewSelection);

    public List<FeatureSelection> getFeatureSelection();

    public void setFeatureSelection(List<FeatureSelection> featureSelection);

    public List<Extension> getExtension();

    public void setExtension(List<Extension> extension);

    public List<Referrer> getReferrer();

    public void setReferrer(List<Referrer> referrer);

    public List<PatternAssociation> getPatternAssociation();

    public void setPatternAssociation(List<PatternAssociation> patternAssociation);
    
}
