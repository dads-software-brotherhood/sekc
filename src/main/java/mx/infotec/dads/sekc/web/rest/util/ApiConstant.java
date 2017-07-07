package mx.infotec.dads.sekc.web.rest.util;

/**
 * UrlUtil for Url composition
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public final class ApiConstant {

    public static final String API_PATH = "/api";
    public static final String MANAGEMENT_AUDITS_RESOURCE = "/management/audits";
    public static final String MANAGEMENT = "/management";
    public static final String SOCIAL = "/social";
    public static final String AUTHENTICATE = "/authenticate";

    private ApiConstant() {
        throw new AssertionError();
    }

}
