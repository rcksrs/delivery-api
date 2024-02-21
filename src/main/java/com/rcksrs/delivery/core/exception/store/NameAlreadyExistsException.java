package com.rcksrs.delivery.core.exception.store;

import com.rcksrs.delivery.core.exception.global.BusinessException;

public class NameAlreadyExistsException extends BusinessException {

    public NameAlreadyExistsException() {
        super("This store name is already in use");
    }
}
