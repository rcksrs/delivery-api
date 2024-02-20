package com.rcksrs.delivery.core.exception.user;

import com.rcksrs.delivery.core.exception.global.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException() {
        super("This email is already registered");
    }
}
