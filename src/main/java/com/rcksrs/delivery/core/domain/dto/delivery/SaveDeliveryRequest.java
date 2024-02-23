package com.rcksrs.delivery.core.domain.dto.delivery;

import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;

public record SaveDeliveryRequest(String info, Long orderId) {
    public Delivery toEntity() {
        var delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.WAITING);
        delivery.setInfo(this.info);

        return delivery;
    }
}
