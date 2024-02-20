package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;

@Data
public class Address {
    private String zipCode;
    private String country;
    private String state;
    private String city;
    private String street;
    private String number;
}
