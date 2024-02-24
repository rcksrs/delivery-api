package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderFilter;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOrderUseCaseImplTest {
    private static final Long ORDER_ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private FindOrderUseCaseImpl findOrderUseCase;

    @Test
    @DisplayName("Should find an order by id when order is saved")
    void shouldFindOrderById() {
        var order = new Order();
        order.setUser(new User());
        order.setStore(new Store());

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        var response = findOrderUseCase.findById(ORDER_ID);

        assertNotNull(response);
        verify(orderRepository).findById(ORDER_ID);
    }

    @Test
    @DisplayName("Should not find order by id when order is not saved")
    void shouldNotFindOrderById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> findOrderUseCase.findById(ORDER_ID));
        verify(orderRepository).findById(ORDER_ID);
    }

    @Test
    @DisplayName("Should find order by filter")
    void shouldFindOrderByFilter() {
        var order = new Order();
        order.setId(ORDER_ID);
        order.setUser(new User());
        order.setStore(new Store());

        var pageable = Pageable.ofSize(10);
        var orderList = new PageImpl<>(Collections.singletonList(order), pageable, 1);

        when(orderRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(orderList);

        var request = new OrderFilter(null, null, null, null, OrderStatus.IN_PROGRESS);
        var response = findOrderUseCase.findByFilter(request, pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertFalse(response.getContent().isEmpty());
        assertEquals(ORDER_ID, response.getContent().get(0).id());
    }

    @Test
    @DisplayName("Should not find any order by filter")
    void shouldNotFindOrderByFilter() {
        var pageable = Pageable.ofSize(10);
        var orderList = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(orderRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(orderList);

        var request = new OrderFilter(null, null, null, null, OrderStatus.IN_PROGRESS);
        var response = findOrderUseCase.findByFilter(request, pageable);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
    }

}