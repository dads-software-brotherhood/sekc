package mx.infotec.dads.sekc.admin.kernel.rest.util;

import org.springframework.http.HttpStatus;

/**
 *
 * @author wisog
 */
public class ResponseWrapper {
    
    private HttpStatus responseCode;
    
    private String errorMessage = "";
    
    private Object responseObject;

    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }
    
    public HttpStatus getResponseCode() {
        return responseCode;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public Object getResponseObject() {
        return responseObject;
    }
    
    @Override
    public String toString() {
        return "{ \"error_message\":\"" + errorMessage + "\", \"data\":" + responseObject + '}';
    }
}
