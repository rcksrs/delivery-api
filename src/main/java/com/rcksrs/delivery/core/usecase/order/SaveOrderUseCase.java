package com.rcksrs.delivery.core.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.domain.dto.order.SaveOrderRequest;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;

public interface SaveOrderUseCase {
    OrderResponse save(SaveOrderRequest request) throws UserNotFoundException, StoreNotFoundException;
}
