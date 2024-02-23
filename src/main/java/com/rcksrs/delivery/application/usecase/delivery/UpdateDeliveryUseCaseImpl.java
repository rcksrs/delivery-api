package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.UpdateDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import com.rcksrs.delivery.core.exception.delivery.DeliveryFinishedException;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
import com.rcksrs.delivery.core.usecase.delivery.UpdateDeliveryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateDeliveryUseCaseImpl implements UpdateDeliveryUseCase {
    private final DeliveryRepository deliveryRepository;

    @Override
    public void update(Long id, UpdateDeliveryRequest request) throws DeliveryNotFoundException, DeliveryFinishedException {
        var delivery = deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
        if (DeliveryStatus.DONE == delivery.getStatus()) throw new DeliveryFinishedException();

        request.update(delivery);
        deliveryRepository.save(delivery);
    }

    @Override
    public void updateStatus(Long id, DeliveryStatus status) throws DeliveryNotFoundException {
        var delivery = deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
        delivery.setStatus(status);
        delivery.setModifiedAt(LocalDateTime.now());
        deliveryRepository.save(delivery);
    }
}
