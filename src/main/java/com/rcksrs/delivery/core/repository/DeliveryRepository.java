package com.rcksrs.delivery.core.repository;

import com.rcksrs.delivery.core.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
