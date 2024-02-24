package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.dto.user.NoAccountUserRequest;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;

public record SaveNoAccountUserOrderRequest(Long storeId, String description, Long quantity, Double price, NoAccountUserRequest user) {

    public Order toEntity() {
        var order = new Order();
        order.setDescription(this.description);
        order.setQuantity(this.quantity);
        order.setPrice(this.price);
        order.setStatus(OrderStatus.ACCEPTED);

        return order;
    }
}
