package com.rcksrs.delivery.core.usecase.order;

import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;

public interface DeleteOrderUseCase {
    void delete(Long id) throws OrderNotFoundException;
}
