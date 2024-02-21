package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;


public record SaveUserRequest(String name, String email,String password, String phone, Address address) {

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
