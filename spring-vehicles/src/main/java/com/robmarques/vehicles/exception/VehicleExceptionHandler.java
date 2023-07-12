package com.robmarques.vehicles.exception;

import com.robmarques.vehicles.exception.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class VehicleExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(VehicleNotFoundException.class)
    ResponseEntity<Object> vehicleNotFoundHandler(VehicleNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(VehicleBadParamsException.class)
    ResponseEntity<Object> vehicleBadRequestHandler(VehicleBadParamsException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}