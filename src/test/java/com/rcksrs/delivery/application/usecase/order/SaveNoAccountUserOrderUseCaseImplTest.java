package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.SaveNoAccountUserOrderRequest;
import com.rcksrs.delivery.core.domain.dto.user.NoAccountUserRequest;
import com.rcksrs.delivery.core.domain.entity.*;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveNoAccountUserOrderUseCaseImplTest {
    private static final Long STORE_ID = 1L;
    private static final String USER_NAME = "name";
    private static final Double PRICE = Double.MAX_VALUE;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private SaveNoAccountUserOrderUseCaseImpl saveNoAccountUserOrderUseCase;

    @Test
    @DisplayName("Should save order when user has no account")
    void shouldSaveOrder() {
        var user = new User();
        user.setName(USER_NAME);

        var store = new Store();
        store.setId(STORE_ID);

        var order = new Order();
        order.setUser(user);
        order.setStore(store);

        var captorForOrder = ArgumentCaptor.forClass(Order.class);
        var captorForUser = ArgumentCaptor.forClass(User.class);

        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(store));
        when(userRepository.save(captorForUser.capture())).thenReturn(user);
        when(orderRepository.save(captorForOrder.capture())).thenReturn(order);

        var userRequest = new NoAccountUserRequest(USER_NAME, null, null);
        var request = new SaveNoAccountUserOrderRequest(STORE_ID, null, null, PRICE, userRequest);

        var response = saveNoAccountUserOrderUseCase.save(request);
        var userSaved = captorForUser.getValue();
        var orderSaved = captorForOrder.getValue();

        assertNotNull(response);

        assertNotNull(userSaved);
        assertNotNull(userSaved.getCreatedAt());
        assertNotNull(userSaved.getRole());
        assertNull(userSaved.getId());
        assertNull(userSaved.getModifiedAt());
        assertFalse(userSaved.isActive());

        assertNotNull(orderSaved);
        assertNotNull(orderSaved.getCreatedAt());
        assertNotNull(orderSaved.getStatus());
        assertNotNull(orderSaved.getUser());
        assertNotNull(orderSaved.getStore());
        assertNull(orderSaved.getId());
        assertNull(orderSaved.getModifiedAt());
        assertTrue(orderSaved.isActive());

        assertEquals(PRICE, orderSaved.getPrice());
        assertEquals(STORE_ID, orderSaved.getStore().getId());
        assertEquals(OrderStatus.ACCEPTED, orderSaved.getStatus());

        assertEquals(USER_NAME, userSaved.getName());
        assertEquals(Role.NO_ACCOUNT, userSaved.getRole());

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(userRepository).save(any(User.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should not save order when store was not found")
    void shouldNotSaveOrderWhenStoreWasNotFound() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        var userRequest = new NoAccountUserRequest(USER_NAME, null, null);
        var request = new SaveNoAccountUserOrderRequest(STORE_ID, null, null, PRICE, userRequest);

        assertThrows(StoreNotFoundException.class, () -> saveNoAccountUserOrderUseCase.save(request));

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(userRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

}