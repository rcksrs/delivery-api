package com.rcksrs.delivery.core.domain.dto.store;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;

import java.time.LocalDateTime;

public record UpdateStoreRequest(String name, String email, String phone, String description, Address address) {
    public Store update(Store store) {
        store.setModifiedAt(LocalDateTime.now());

        if (this.name != null) store.setName(this.name);
        if (this.email != null) store.setEmail(this.email);
        if (this.phone != null) store.setPhone(this.phone);
        if (this.description != null) store.setDescription(this.description);
        if (this.address != null) store.setAddress(this.address);

        return store;
    }
}
