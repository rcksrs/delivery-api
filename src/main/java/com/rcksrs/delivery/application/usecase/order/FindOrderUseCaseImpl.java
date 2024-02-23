package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderFilter;
import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.usecase.order.FindOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<OrderResponse> findByFilter(OrderFilter filter, Pageable pageable) {
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withIgnorePaths("createdAt")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(filter.filter(), matcher);
        return orderRepository.findAll(example, pageable).map(OrderResponse::new);
    }
}
