package com.rcksrs.delivery.core.exception.store;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class StoreNotFoundException extends EntityNotFoundException {

    public StoreNotFoundException() {
        super("Store was not found");
    }
}
