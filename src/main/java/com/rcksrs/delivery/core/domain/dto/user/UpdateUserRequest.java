package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.User;

import java.time.LocalDateTime;

public record UpdateUserRequest(String name, String password, String phone, Address address) {

    public User update(User user) {
        user.setModifiedAt(LocalDateTime.now());
        if (this.name != null) user.setName(this.name);
        if (this.password != null) user.setPassword(this.password);
        if (this.phone != null) user.setPhone(this.phone);
        if (this.address != null) user.setAddress(this.address);

        return user;
    }
}
