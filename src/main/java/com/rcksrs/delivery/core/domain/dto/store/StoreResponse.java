package com.rcksrs.delivery.core.domain.dto.store;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;

public record StoreResponse(Long id, String name, String email, String phone, String description, Address address) {
    public StoreResponse(Store store) {
        this(store.getId(), store.getName(), store.getEmail(), store.getPhone(), store.getDescription(), store.getAddress());
    }
}
