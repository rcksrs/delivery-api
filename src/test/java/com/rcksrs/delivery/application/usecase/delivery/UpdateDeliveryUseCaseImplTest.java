package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.UpdateDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import com.rcksrs.delivery.core.exception.delivery.DeliveryFinishedException;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
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
class UpdateDeliveryUseCaseImplTest {
    private static final Long DELIVERY_ID = 1L;
    private static final String INFO = "info";

    @Mock
    private DeliveryRepository deliveryRepository;


    @InjectMocks
    private UpdateDeliveryUseCaseImpl updateDeliveryUseCase;

    @Test
    @DisplayName("Should update delivery")
    void shouldUpdateDelivery() {
        var captor = ArgumentCaptor.forClass(Delivery.class);

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(new Delivery()));
        when(deliveryRepository.save(captor.capture())).thenReturn(new Delivery());

        var request = new UpdateDeliveryRequest(INFO, null);
        updateDeliveryUseCase.update(DELIVERY_ID, request);

        var deliveryUpdated = captor.getValue();

        assertNotNull(deliveryUpdated);
        assertNotNull(deliveryUpdated.getInfo());
        assertNotNull(deliveryUpdated.getCreatedAt());
        assertNotNull(deliveryUpdated.getModifiedAt());

        assertNull(deliveryUpdated.getId());
        assertNull(deliveryUpdated.getStatus());
        assertNull(deliveryUpdated.getLocation());
        assertNull(deliveryUpdated.getOrder());

        assertTrue(deliveryUpdated.isActive());

        assertEquals(INFO, deliveryUpdated.getInfo());

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should not update delivery when delivery was not found")
    void shouldNotUpdateDelivery() {
        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.empty());

        var request = new UpdateDeliveryRequest(INFO, null);

        assertThrows(DeliveryNotFoundException.class, () -> updateDeliveryUseCase.update(DELIVERY_ID, request));

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update delivery when delivery status is done")
    void shouldNotUpdateDeliveryWhenStatusIsDone() {
        var delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.DONE);

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(delivery));

        var request = new UpdateDeliveryRequest(INFO, null);

        assertThrows(DeliveryFinishedException.class, () -> updateDeliveryUseCase.update(DELIVERY_ID, request));

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update delivery status")
    void shouldUpdateDeliveryStatus() {
        var captor = ArgumentCaptor.forClass(Delivery.class);

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(new Delivery()));
        when(deliveryRepository.save(captor.capture())).thenReturn(new Delivery());

        updateDeliveryUseCase.updateStatus(DELIVERY_ID, DeliveryStatus.IN_PROGRESS);
        var deliveryUpdated = captor.getValue();

        assertNotNull(deliveryUpdated);
        assertNotNull(deliveryUpdated.getStatus());
        assertNotNull(deliveryUpdated.getModifiedAt());

        assertTrue(deliveryUpdated.isActive());

        assertEquals(DeliveryStatus.IN_PROGRESS, deliveryUpdated.getStatus());

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should not update delivery status when delivery was not found")
    void shouldNotUpdateDeliveryStatus() {
        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> updateDeliveryUseCase.updateStatus(DELIVERY_ID, DeliveryStatus.CANCELED));

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository, never()).save(any());
    }

}