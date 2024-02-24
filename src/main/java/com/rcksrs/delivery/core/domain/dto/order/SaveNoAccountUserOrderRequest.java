package com.rcksrs.delivery.core.domain.dto.order;

import com.rcksrs.delivery.core.domain.dto.user.NoAccountUserRequest;
import com.rcksrs.delivery.core.domain.entity.Order;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record SaveNoAccountUserOrderRequest(
        @NotNull Long storeId,
        @NotBlank @Size(min = 20, max = 255) String description,
        @Positive Long quantity,
        @Positive Double price,
        @NotNull @Valid NoAccountUserRequest user) {

    public Order toEntity() {
        var order = new Order();
        order.setDescription(this.description);
        order.setQuantity(this.quantity);
        order.setPrice(this.price);
        order.setStatus(OrderStatus.ACCEPTED);

        return order;
    }
}
