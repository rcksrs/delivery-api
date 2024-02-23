package com.rcksrs.delivery.core.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.UpdateDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import com.rcksrs.delivery.core.exception.delivery.DeliveryFinishedException;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;

public interface UpdateDeliveryUseCase {
    void update(Long id, UpdateDeliveryRequest request) throws DeliveryNotFoundException, DeliveryFinishedException;
    void updateStatus(Long id, DeliveryStatus status) throws DeliveryNotFoundException;
}
