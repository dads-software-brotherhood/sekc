package mx.infotec.dads.sekc.admin.practice.catalog.dto;

public enum ResourcesTypes {
    CHECKLIST("1","Checklist"),
    FORM("2","Form"),
    GENERAL_DOCUMENT("3","General document"),
    INFO("4","Info"),
    IMAGE("5","Image"),
    WORKPRODUCT("6","Workproduct"),
    URL("7","URL");
    
    private final String id;
    private final String value;

    private ResourcesTypes(String id, String value) {
        this.id = id;
        this.value = value;
    }
    
    public String getId() {
        return id;
    }
    
    public String getValue() {
        return value;
    }
}