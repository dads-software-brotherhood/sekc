package mx.infotec.dads.sekc.admin.kernel.dto;

import java.util.List;

/**
 *
 * @author wisog
 */
public interface ElementGroupDto extends LanguageElementDto{
    
    public String getName();

    public void setName(String name);
    
    public String getBriefDescription();
    
    public void setBriefDescription(String briefDescription);
    
    public String getDescription();

    public void setDescription(String description);
    
    public Icon getIcon();

    public void setIcon(Icon icon);
    
    public List<MergeResolution> getMergeResolution();

    public void setMergeResolution(List<MergeResolution> mergeResolution);

    public List<OwnedElement> getOwnedElements();

    public void setOwnedElements(List<OwnedElement> ownedElements);

    public List<ReferredElement> getReferredElements();

    public void setReferredElements(List<ReferredElement> referredElements);
}