package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.entity.Order;

import java.time.LocalDateTime;

public record UpdateOrderRequest(String description, Long quantity, Double price) {

    public Order update(Order order) {
        order.setModifiedAt(LocalDateTime.now());
        if (this.description != null) order.setDescription(this.description);
        if (this.quantity != null) order.setQuantity(this.quantity);
        if (this.price != null) order.setPrice(this.price);

        return order;
    }
}
