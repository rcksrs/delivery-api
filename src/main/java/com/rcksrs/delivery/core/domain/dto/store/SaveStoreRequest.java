package com.rcksrs.delivery.core.domain.dto.store;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;

public record SaveStoreRequest(String name, String email, String phone, String description, Address address) {
    public Store toEntity() {
        var store = new Store();
        store.setName(this.name);
        store.setEmail(this.email);
        store.setPhone(this.phone);
        store.setDescription(this.description);
        store.setAddress(this.address);

        return store;
    }
}
