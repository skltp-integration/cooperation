package se.skltp.cooperation.web.rest.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Peter Merikan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationError extends ProblemDetail{

    private List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationError() {

    }

    public void addFieldError(String field, String code, String message) {
        FieldError error = new FieldError(field, code, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
