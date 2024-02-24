package com.rcksrs.delivery.core.exception.delivery;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class DeliveryInProgressException extends EntityNotFoundException {

    public DeliveryInProgressException() {
        super("There is a delivery in progress or done for this order");
    }
}
