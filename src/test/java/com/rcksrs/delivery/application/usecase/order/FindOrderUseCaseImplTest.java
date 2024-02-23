package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.entity.Order;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}