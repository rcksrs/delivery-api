package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.UpdateOrderRequest;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.exception.order.OrderFinishedException;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.usecase.order.UpdateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public void update(Long id, UpdateOrderRequest request) throws OrderNotFoundException, OrderFinishedException {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (OrderStatus.FINISHED == order.getStatus()) throw new OrderFinishedException();

        var orderUpdated = request.update(order);
        orderRepository.save(orderUpdated);
    }

    @Override
    public void updateStatus(Long id, OrderStatus status) throws OrderNotFoundException {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        order.setStatus(status);
        order.setModifiedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}
