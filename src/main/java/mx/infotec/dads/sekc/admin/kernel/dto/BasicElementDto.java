package mx.infotec.dads.sekc.admin.kernel.dto;

/**
 *
 * @author wisog
 */
public interface BasicElementDto extends LanguageElementDto{
    
    public String getName();

    public void setName(String name);
    
    public String getBriefDescription();
    
    public void setBriefDescription(String briefDescription);
    
    public String getDescription();

    public void setDescription(String description);

}
