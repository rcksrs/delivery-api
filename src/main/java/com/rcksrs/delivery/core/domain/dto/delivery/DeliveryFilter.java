package com.rcksrs.delivery.core.domain.dto.delivery;

import com.rcksrs.delivery.core.domain.entity.*;

public record DeliveryFilter(Long orderId, Long storeId, Long userId, String info, DeliveryStatus deliveryStatus) {
    public Delivery filter() {
        var order = new Order();
        order.setId(this.orderId);
        order.setCreatedAt(null);

        var delivery = new Delivery();
        delivery.setStatus(this.deliveryStatus);
        delivery.setInfo(this.info);
        delivery.setOrder(order);
        delivery.setCreatedAt(null);

        if (this.userId != null) {
            var user = new User();
            user.setId(this.userId);
            user.setCreatedAt(null);

            order.setUser(user);
        }

        if (this.storeId != null) {
            var store = new Store();
            store.setId(this.storeId);
            store.setCreatedAt(null);

            order.setStore(store);
        }

        return delivery;
    }
}
