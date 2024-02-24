package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Address {
    @NotBlank
    private String zipCode;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String number;
}
