package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.UpdateOrderRequest;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.order.OrderFinishedException;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderUseCaseImplTest {
    private static final Long ORDER_ID = 1L;
    private static final Long QUANTITY = Long.MAX_VALUE;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UpdateOrderUseCaseImpl updateOrderUseCase;

    @Test
    @DisplayName("Should update order")
    void shouldUpdateOrder() {
        var order = new Order();
        order.setUser(new User());
        order.setStore(new Store());

        var captor = ArgumentCaptor.forClass(Order.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(captor.capture())).thenReturn(new Order());

        var request = new UpdateOrderRequest(null, QUANTITY, null);
        updateOrderUseCase.update(ORDER_ID, request);

        var orderUpdated = captor.getValue();

        assertNotNull(orderUpdated);
        assertNotNull(orderUpdated.getQuantity());
        assertNotNull(orderUpdated.getCreatedAt());
        assertNotNull(orderUpdated.getModifiedAt());
        assertNotNull(orderUpdated.getUser());
        assertNotNull(orderUpdated.getStore());

        assertNull(orderUpdated.getId());
        assertNull(orderUpdated.getDescription());
        assertNull(orderUpdated.getPrice());
        assertNull(orderUpdated.getStatus());

        assertTrue(orderUpdated.isActive());

        assertEquals(QUANTITY, orderUpdated.getQuantity());

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should not update order when order was not found")
    void shouldNotUpdateOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        var request = new UpdateOrderRequest(null, QUANTITY, null);

        assertThrows(OrderNotFoundException.class, () -> updateOrderUseCase.update(ORDER_ID, request));

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update order when order status is finished")
    void shouldNotUpdateOrderWhenStatusIsFinished() {
        var order = new Order();
        order.setStatus(OrderStatus.FINISHED);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        var request = new UpdateOrderRequest(null, QUANTITY, null);

        assertThrows(OrderFinishedException.class, () -> updateOrderUseCase.update(ORDER_ID, request));

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update order status")
    void shouldUpdateOrderStatus() {
        var captor = ArgumentCaptor.forClass(Order.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        when(orderRepository.save(captor.capture())).thenReturn(new Order());

        updateOrderUseCase.updateStatus(ORDER_ID, OrderStatus.IN_PROGRESS);
        var orderUpdated = captor.getValue();

        assertNotNull(orderUpdated);
        assertNotNull(orderUpdated.getStatus());
        assertNotNull(orderUpdated.getModifiedAt());

        assertTrue(orderUpdated.isActive());

        assertEquals(OrderStatus.IN_PROGRESS, orderUpdated.getStatus());

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should not update order status when order was not found")
    void shouldNotUpdateOrderStatus() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> updateOrderUseCase.updateStatus(ORDER_ID, OrderStatus.FINISHED));

        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository, never()).save(any());
    }

}