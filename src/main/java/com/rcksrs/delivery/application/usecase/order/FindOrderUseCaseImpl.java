package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.usecase.order.FindOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrderUseCaseImpl implements FindOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse findById(Long id) throws OrderNotFoundException {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return new OrderResponse(order);
    }
}
