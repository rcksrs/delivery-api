package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
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
class DeleteDeliveryUseCaseImplTest {
    private static final Long DELIVERY_ID = 1L;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeleteDeliveryUseCaseImpl deleteDeliveryUseCase;

    @Test
    @DisplayName("Should delete delivery")
    void shouldDeleteDelivery() {
        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(new Delivery()));

        deleteDeliveryUseCase.delete(DELIVERY_ID);

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository).delete(any(Delivery.class));
    }

    @Test
    @DisplayName("Should not delete delivery when delivery was not found")
    void shouldNotDeleteDelivery() {
        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> deleteDeliveryUseCase.delete(DELIVERY_ID));

        verify(deliveryRepository).findById(DELIVERY_ID);
        verify(deliveryRepository, never()).delete(any());
    }

}