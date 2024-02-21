package com.rcksrs.delivery.core.exception.order;

import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;

public class OrderFinishedException extends EntityNotFoundException {

    public OrderFinishedException() {
        super("Order is finished and can't be updated");
    }
}
