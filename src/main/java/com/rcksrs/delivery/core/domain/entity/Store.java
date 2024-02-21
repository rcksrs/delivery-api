package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Store extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private String description;

    @Embedded
    private Address address;
}
