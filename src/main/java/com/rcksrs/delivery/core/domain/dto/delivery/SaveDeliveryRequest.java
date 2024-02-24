package com.rcksrs.delivery.core.domain.dto.delivery;

import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SaveDeliveryRequest(
        @NotBlank @Size(min = 20, max = 255) String info,
        @NotNull Long orderId) {
    public Delivery toEntity() {
        var delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.WAITING);
        delivery.setInfo(this.info);

        return delivery;
    }
}
