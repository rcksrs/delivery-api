package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.SaveOrderRequest;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.repository.UserRepository;
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
class SaveOrderUseCaseImplTest {
    private static final Long USER_ID = 1L;
    private static final Long STORE_ID = 2L;
    private static final String DESCRIPTION = "description";
    private static final Long QUANTITY = Long.MAX_VALUE;
    private static final Double PRICE = Double.MAX_VALUE;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private SaveOrderUseCaseImpl saveOrderUseCase;

    @Test
    @DisplayName("Should save new order")
    void shouldSaveOrder() {
        var user = new User();
        user.setId(USER_ID);

        var store = new Store();
        store.setId(STORE_ID);

        var order = new Order();
        order.setUser(user);
        order.setStore(store);

        var captor = ArgumentCaptor.forClass(Order.class);

        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(user));
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(store));
        when(orderRepository.save(captor.capture())).thenReturn(order);

        var request = new SaveOrderRequest(DESCRIPTION, QUANTITY, PRICE, USER_ID, STORE_ID);
        var response = saveOrderUseCase.save(request);
        var orderSaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(orderSaved);
        assertNotNull(orderSaved.getCreatedAt());
        assertNotNull(orderSaved.getStatus());

        assertNull(orderSaved.getId());
        assertNull(orderSaved.getModifiedAt());

        assertTrue(orderSaved.isActive());

        assertEquals(OrderStatus.ACCEPTED, orderSaved.getStatus());
        assertEquals(DESCRIPTION, orderSaved.getDescription());
        assertEquals(QUANTITY, orderSaved.getQuantity());
        assertEquals(PRICE, orderSaved.getPrice());
        assertEquals(USER_ID, orderSaved.getUser().getId());
        assertEquals(STORE_ID, orderSaved.getStore().getId());

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should not save order when user id was not found")
    void shouldNotSaveOrderWhenUserWasNotFound() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());
        var request = new SaveOrderRequest(DESCRIPTION, QUANTITY, PRICE, USER_ID, STORE_ID);

        assertThrows(UserNotFoundException.class, () -> saveOrderUseCase.save(request));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(storeRepository, never()).findByIdAndActiveTrue(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not save order when store id was not found")
    void shouldNotSaveOrderWhenStoreWasNotFound() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        var request = new SaveOrderRequest(DESCRIPTION, QUANTITY, PRICE, USER_ID, STORE_ID);

        assertThrows(StoreNotFoundException.class, () -> saveOrderUseCase.save(request));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(orderRepository, never()).save(any());
    }

}