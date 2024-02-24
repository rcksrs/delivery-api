package com.rcksrs.delivery.core.exception.global;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Access denied for this resource");
    }
}
