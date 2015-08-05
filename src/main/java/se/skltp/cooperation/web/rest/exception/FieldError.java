package se.skltp.cooperation.web.rest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Peter Merikan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FieldError {

    private String field;
    private String code;
    private String message;

    public FieldError(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
