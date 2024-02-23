package com.rcksrs.delivery.core.exception.delivery;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class DeliveryNotFoundException extends EntityNotFoundException {

    public DeliveryNotFoundException() {
        super("Delivery was not found");
    }
}
