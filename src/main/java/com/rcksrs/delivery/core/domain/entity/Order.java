package com.rcksrs.delivery.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    private String description;
    private Long quantity;
    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Delivery> deliveries;
}
