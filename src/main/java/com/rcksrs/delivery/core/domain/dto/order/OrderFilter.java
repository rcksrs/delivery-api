package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.domain.entity.User;

public record OrderFilter(Long userId, String userName, Long storeId, String storeName, OrderStatus status) {
    public Order filter() {
        var order = new Order();
        order.setStatus(status);
        order.setCreatedAt(null);

        if (this.userId != null || this.userName != null) {
            var user = new User();
            user.setId(this.userId);
            user.setName(this.userName);
            user.setRole(null);
            user.setCreatedAt(null);

            order.setUser(user);
        }

        if (this.storeId != null || this.storeName != null) {
            var store = new Store();
            store.setId(this.storeId);
            store.setName(this.storeName);
            store.setCreatedAt(null);

            order.setStore(store);
        }

        return order;
    }
}
