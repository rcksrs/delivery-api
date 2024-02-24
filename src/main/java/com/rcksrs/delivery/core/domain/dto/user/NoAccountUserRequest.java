package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;


public record NoAccountUserRequest(String name, String phone, Address address) {

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
