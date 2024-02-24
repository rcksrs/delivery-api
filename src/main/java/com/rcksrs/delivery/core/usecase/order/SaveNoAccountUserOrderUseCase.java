package com.rcksrs.delivery.core.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.domain.dto.order.SaveNoAccountUserOrderRequest;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;

public interface SaveNoAccountUserOrderUseCase {
    OrderResponse save(SaveNoAccountUserOrderRequest request) throws StoreNotFoundException;
}
