package com.robmarques.vehicles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class VehicleBadParamsException extends RuntimeException {
    public VehicleBadParamsException(BindingResult bindingResult) {
        super(getValidationErrorMessage(bindingResult));
    }

    private static String getValidationErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(bindingResult.getErrorCount()).append(" error(s): ");
        for (ObjectError error : bindingResult.getAllErrors()) {
            sb.append("[").append(error).append("] ");
        }
        return sb.toString();
    }
}