package com.makaia.flightReservation.exception;

public class BadRequestsException extends RuntimeException{
    public BadRequestsException(String message) {
        super(message);
    }
}
