/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
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
