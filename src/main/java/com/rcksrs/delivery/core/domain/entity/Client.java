package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {
    private String name;
    private String email;
    private String logo;
    private String description;
    private List<Address> addresses;
}
