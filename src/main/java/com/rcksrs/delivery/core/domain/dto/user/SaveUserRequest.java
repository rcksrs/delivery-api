package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.*;


public record SaveUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 18) String password,
        @NotBlank @Size(min = 9, max = 13) String phone,
        @NotNull @Valid Address address) {

    public User toEntity() {
        var user = new User();
        user.setRole(Role.USER);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhone(this.phone);
        user.setAddress(this.address);

        return user;
    }
}
