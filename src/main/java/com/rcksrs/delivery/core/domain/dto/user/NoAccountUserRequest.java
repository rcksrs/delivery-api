package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public record NoAccountUserRequest(
        @NotBlank String name,
        @NotBlank @Size(min = 9, max = 13) String phone,
        @NotNull @Valid Address address) {

    public User toEntity() {
        var user = new User();
        user.setRole(Role.NO_ACCOUNT);
        user.setActive(false);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setAddress(this.address);

        return user;
    }
}
