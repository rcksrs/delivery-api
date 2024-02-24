package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.SaveDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.*;
import com.rcksrs.delivery.core.exception.delivery.DeliveryInProgressException;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveDeliveryUseCaseImplTest {
    private static final Long ORDER_ID = 1L;
    private static final String INFO = "info";
    private static final String CITY = "city";

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private SaveDeliveryUseCaseImpl saveDeliveryUseCase;

    @Test
    @DisplayName("Should save delivery")
    void shouldSaveDelivery() {
        var address = new Address();
        address.setCity(CITY);

        var user = new User();
        user.setAddress(address);

        var order = new Order();
        order.setUser(user);
        order.setStore(new Store());

        var delivery = new Delivery();
        delivery.setOrder(order);

        var captor = ArgumentCaptor.forClass(Delivery.class);

        when(deliveryRepository.existsByOrderIdAndStatusNot(anyLong(), any(DeliveryStatus.class))).thenReturn(false);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(deliveryRepository.save(captor.capture())).thenReturn(delivery);

        var request = new SaveDeliveryRequest(INFO, ORDER_ID);
        var response = saveDeliveryUseCase.save(request);
        var deliverySaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(deliverySaved);
        assertNotNull(deliverySaved.getInfo());
        assertNotNull(deliverySaved.getCreatedAt());
        assertNotNull(deliverySaved.getStatus());
        assertNotNull(deliverySaved.getLocation());
        assertNotNull(deliverySaved.getOrder());
        assertNotNull(deliverySaved.getOrder().getStore());
        assertNotNull(deliverySaved.getOrder().getUser());

        assertNull(deliverySaved.getId());
        assertNull(deliverySaved.getModifiedAt());

        assertTrue(deliverySaved.isActive());

        assertEquals(INFO, deliverySaved.getInfo());
        assertEquals(CITY, deliverySaved.getLocation().getCity());
        assertEquals(DeliveryStatus.WAITING, deliverySaved.getStatus());
        assertEquals(OrderStatus.FINISHED, deliverySaved.getOrder().getStatus());


        verify(deliveryRepository).existsByOrderIdAndStatusNot(ORDER_ID, DeliveryStatus.CANCELED);
        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository).save(any(Order.class));
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should not save delivery when there is already a no cancelled order")
    void shouldNotSaveDeliveryWhenNoCancelledOrderExists() {
        when(deliveryRepository.existsByOrderIdAndStatusNot(anyLong(), any(DeliveryStatus.class))).thenReturn(true);

        var request = new SaveDeliveryRequest(INFO, ORDER_ID);
        assertThrows(DeliveryInProgressException.class, () -> saveDeliveryUseCase.save(request));

        verify(deliveryRepository).existsByOrderIdAndStatusNot(ORDER_ID, DeliveryStatus.CANCELED);
        verify(orderRepository, never()).findById(any());
        verify(orderRepository, never()).save(any());
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not save delivery when order was not found")
    void shouldNotSaveDeliveryWhenOrderWasNotFound() {
        when(deliveryRepository.existsByOrderIdAndStatusNot(anyLong(), any(DeliveryStatus.class))).thenReturn(false);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        var request = new SaveDeliveryRequest(INFO, ORDER_ID);
        assertThrows(OrderNotFoundException.class, () -> saveDeliveryUseCase.save(request));

        verify(deliveryRepository).existsByOrderIdAndStatusNot(ORDER_ID, DeliveryStatus.CANCELED);
        verify(orderRepository).findById(ORDER_ID);
        verify(orderRepository, never()).save(any());
        verify(deliveryRepository, never()).save(any());
    }

}