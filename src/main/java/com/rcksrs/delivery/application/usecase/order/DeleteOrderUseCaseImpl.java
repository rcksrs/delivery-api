package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.usecase.order.DeleteOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderUseCaseImpl implements DeleteOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public void delete(Long id) throws OrderNotFoundException {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderRepository.delete(order);
    }
}
