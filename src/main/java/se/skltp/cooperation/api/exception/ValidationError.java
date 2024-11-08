/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.api.exception;

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
