package com.rcksrs.delivery.core.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderFilter;
import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindOrderUseCase {
    OrderResponse findById(Long id) throws OrderNotFoundException;
    Page<OrderResponse> findByFilter(OrderFilter filter, Pageable pageable);
}