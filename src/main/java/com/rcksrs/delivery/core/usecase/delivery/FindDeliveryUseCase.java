package com.rcksrs.delivery.core.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryFilter;
import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryResponse;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindDeliveryUseCase {
    DeliveryResponse findById(Long id) throws DeliveryNotFoundException;
    Page<DeliveryResponse> findByFilter(DeliveryFilter filter, Pageable pageable);
}

