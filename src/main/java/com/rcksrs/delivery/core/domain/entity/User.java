package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String document;
    private String phone;
    private Address address;
    private Role role;
}
