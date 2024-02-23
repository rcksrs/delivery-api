package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
import com.rcksrs.delivery.core.usecase.delivery.DeleteDeliveryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDeliveryUseCaseImpl implements DeleteDeliveryUseCase {
    private final DeliveryRepository deliveryRepository;

    @Override
    public void delete(Long id) throws DeliveryNotFoundException {
        var delivery = deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
        deliveryRepository.delete(delivery);
    }
}
