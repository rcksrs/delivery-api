package com.rcksrs.delivery.core.domain.dto.delivery;

import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;

public record DeliveryResponse(String info, DeliveryStatus status, Address location, OrderResponse order) {
    public DeliveryResponse(Delivery delivery) {
        this(delivery.getInfo(), delivery.getStatus(), delivery.getLocation(), new OrderResponse(delivery.getOrder()));
    }
}
