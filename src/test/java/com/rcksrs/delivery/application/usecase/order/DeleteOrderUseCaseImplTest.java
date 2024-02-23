package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteOrderUseCaseImplTest {
    private static final Long ORDER_ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DeleteOrderUseCaseImpl deleteOrderUseCase;

    @Test
    @DisplayName("Should delete order")
    void shouldDeleteOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));

        deleteOrderUseCase.delete(ORDER_ID);

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository).delete(any(Order.class));
    }

    @Test
    @DisplayName("Should not delete order when order was not found")
    void shouldNotDeleteOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> deleteOrderUseCase.delete(ORDER_ID));

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository, never()).delete(any());
    }

}