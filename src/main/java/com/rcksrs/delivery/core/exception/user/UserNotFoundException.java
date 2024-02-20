package com.rcksrs.delivery.core.exception.user;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("No user was found from the provided field");
    }
}
