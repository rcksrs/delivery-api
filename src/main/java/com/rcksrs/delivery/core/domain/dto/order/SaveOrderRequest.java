package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;

public record SaveOrderRequest(String description, Long quantity, Double price, Long userId, Long storeId) {

    public Order toEntity() {
        var order = new Order();
        order.setDescription(this.description);
        order.setQuantity(this.quantity);
        order.setPrice(this.price);
        order.setStatus(OrderStatus.ACCEPTED);

        return order;
    }
}
