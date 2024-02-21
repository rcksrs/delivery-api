package com.rcksrs.delivery.core.exception.order;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException() {
        super("Order was not found");
    }
}
