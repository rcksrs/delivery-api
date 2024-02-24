package com.rcksrs.delivery.core.repository;

import com.rcksrs.delivery.core.domain.entity.Delivery;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    boolean existsByOrderIdAndStatusNot(Long orderId, DeliveryStatus status);

}
