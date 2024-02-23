package com.rcksrs.delivery.core.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryResponse;
import com.rcksrs.delivery.core.domain.dto.delivery.SaveDeliveryRequest;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;

public interface SaveDeliveryUseCase {
    DeliveryResponse save(SaveDeliveryRequest request) throws OrderNotFoundException;
}
