package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;

public record OrderResponse(Long id,
                            String description,
                            Long quantity,
                            Double price,
                            OrderStatus status,
                            Long userId,
                            String userName,
                            Long storeId,
                            String storeName) {

    public OrderResponse(Order order) {
        this(order.getId(),
                order.getDescription(),
                order.getQuantity(),
                order.getPrice(),
                order.getStatus(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getStore().getId(),
                order.getStore().getName()
        );
    }
}
