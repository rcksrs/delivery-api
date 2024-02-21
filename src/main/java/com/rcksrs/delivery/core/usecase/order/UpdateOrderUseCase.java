package com.rcksrs.delivery.core.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.UpdateOrderRequest;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.exception.order.OrderFinishedException;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;

public interface UpdateOrderUseCase {
    void update(Long id, UpdateOrderRequest request) throws OrderNotFoundException, OrderFinishedException;
    void updateStatus(Long id, OrderStatus status) throws OrderNotFoundException;
}
