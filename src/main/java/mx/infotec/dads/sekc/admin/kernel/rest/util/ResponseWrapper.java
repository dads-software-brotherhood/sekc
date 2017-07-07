package mx.infotec.dads.sekc.admin.kernel.rest.util;

import org.springframework.http.HttpStatus;

/**
 *
 * @author wisog
 */
public class ResponseWrapper {
    
    private HttpStatus response_code;
    
    private String error_message = "";
    
    private Object responseObject;

    public void setResponse_code(HttpStatus response_code) {
        this.response_code = response_code;
    }
    
    public HttpStatus getResponse_code() {
        return response_code;
    }
    
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
    
    public String getError_message() {
        return error_message;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public Object getResponseObject() {
        return responseObject;
    }
    
    @Override
    public String toString() {
        return "{ \"error_message\":\"" + error_message + "\", \"data\":" + responseObject + '}';
    }
}
