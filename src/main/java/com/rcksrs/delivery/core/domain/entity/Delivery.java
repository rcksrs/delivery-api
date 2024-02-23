package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Delivery extends BaseEntity {
    private String info;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address location;

    @ManyToOne
    private Order order;
}
