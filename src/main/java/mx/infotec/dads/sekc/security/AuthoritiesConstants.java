package mx.infotec.dads.sekc.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    
    public static final String CONSULTANT = "ROLE_CONSULTANT";
    
    public static final String VALIDATOR = "ROLE_VALIDATOR";
    
    public static final String PRACTICING = "ROLE_PRACTICING";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    private AuthoritiesConstants() {
    }
}
