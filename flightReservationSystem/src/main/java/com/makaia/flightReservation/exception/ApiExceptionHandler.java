package com.makaia.flightReservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BadRequestsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> badRequestException(BadRequestsException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("America/Bogota"))
        );
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> internalServerErrorException(NotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("America/Bogota"))
        );
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> internalServerErrorException(InternalServerErrorException e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("America/Bogota"))
        );
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>("Type error in parameter '" + ex.getName() + "'. Expected " + ex.getRequiredType().getSimpleName(), HttpStatus.BAD_REQUEST);
    }
}
