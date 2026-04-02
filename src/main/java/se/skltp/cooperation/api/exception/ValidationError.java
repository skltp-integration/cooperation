/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
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
