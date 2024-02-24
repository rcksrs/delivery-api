package com.rcksrs.delivery.core.domain.dto.store;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SaveStoreRequest(
        @NotBlank String name,
        String email,
        @NotBlank @Size(min = 9, max = 13) String phone,
        String description,
        @NotNull @Valid Address address) {
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
