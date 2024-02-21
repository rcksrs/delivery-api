package com.rcksrs.delivery.core.exception.user;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("User was not found");
    }
}
