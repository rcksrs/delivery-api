package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryFilter;
import com.rcksrs.delivery.core.domain.entity.*;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
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
class FindDeliveryUseCaseImplTest {
    private static final Long DELIVERY_ID = 1L;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private FindDeliveryUseCaseImpl findDeliveryUseCase;

    @Test
    @DisplayName("Should find delivery by id when delivery is saved")
    void shouldFindDeliveryById() {
        var order = new Order();
        order.setUser(new User());
        order.setStore(new Store());

        var delivery = new Delivery();
        delivery.setOrder(order);

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(delivery));
        var response = findDeliveryUseCase.findById(DELIVERY_ID);

        assertNotNull(response);
        verify(deliveryRepository).findById(DELIVERY_ID);
    }

    @Test
    @DisplayName("Should not find delivery by id when delivery is not saved")
    void shouldNotFindDeliveryById() {
        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> findDeliveryUseCase.findById(DELIVERY_ID));
        verify(deliveryRepository).findById(DELIVERY_ID);
    }

    @Test
    @DisplayName("Should find delivery by filter")
    void shouldFindDeliveryByFilter() {
        var order = new Order();
        order.setUser(new User());
        order.setStore(new Store());

        var delivery = new Delivery();
        delivery.setId(DELIVERY_ID);
        delivery.setOrder(order);

        var pageable = Pageable.ofSize(1);
        var deliveryList = new PageImpl<>(Collections.singletonList(delivery), pageable, 1);

        when(deliveryRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(deliveryList);

        var request = new DeliveryFilter(null, null, null, null, DeliveryStatus.DONE);
        var response = findDeliveryUseCase.findByFilter(request, pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertFalse(response.getContent().isEmpty());
        assertEquals(DELIVERY_ID, response.getContent().get(0).id());
    }

    @Test
    @DisplayName("Should not find any delivery by filter")
    void shouldNotFindDeliveryByFilter() {
        var pageable = Pageable.ofSize(10);
        var deliveryList = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(deliveryRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(deliveryList);

        var request = new DeliveryFilter(null, null, null, null, DeliveryStatus.DONE);
        var response = findDeliveryUseCase.findByFilter(request, pageable);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
    }

}