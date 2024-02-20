package com.rcksrs.delivery.core.domain.dto.user;

import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.User;

public record UserResponse(Long id, String name, String email, String phone, Address address) {

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
    }
}
