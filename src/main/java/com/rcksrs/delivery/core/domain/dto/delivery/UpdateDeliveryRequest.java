package com.rcksrs.delivery.core.domain.dto.delivery;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Delivery;

import javax.validation.Valid;
import java.time.LocalDateTime;

public record UpdateDeliveryRequest(String info, @Valid Address address) {
    public void update(Delivery delivery) {
        delivery.setModifiedAt(LocalDateTime.now());

        if (this.info != null) delivery.setInfo(this.info);
        if (this.address != null) delivery.setLocation(this.address);
    }
}
