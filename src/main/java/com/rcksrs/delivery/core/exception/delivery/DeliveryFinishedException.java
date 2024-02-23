package com.rcksrs.delivery.core.exception.delivery;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class DeliveryFinishedException extends EntityNotFoundException {

    public DeliveryFinishedException() {
        super("Delivery is finished and can't be updated");
    }
}
