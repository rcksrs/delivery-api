package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
