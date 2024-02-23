package com.rcksrs.delivery.core.usecase.delivery;

import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;

public interface DeleteDeliveryUseCase {
    void delete(Long id) throws DeliveryNotFoundException;
}
