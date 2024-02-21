package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;

public record OrderResponse(Long id,
                            String description,
                            Long quantity,
                            Double price,
                            OrderStatus status,
                            String user,
                            String store) {

    public OrderResponse(Order order) {
        this(order.getId(),
                order.getDescription(),
                order.getQuantity(),
                order.getPrice(),
                order.getStatus(),
                order.getUser().getName(),
                order.getStore().getName()
        );
    }
}
