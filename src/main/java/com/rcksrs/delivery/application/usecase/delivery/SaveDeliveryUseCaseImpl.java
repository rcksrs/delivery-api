package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryResponse;
import com.rcksrs.delivery.core.domain.dto.delivery.SaveDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.exception.delivery.DeliveryInProgressException;
import com.rcksrs.delivery.core.exception.order.OrderNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.usecase.delivery.SaveDeliveryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaveDeliveryUseCaseImpl implements SaveDeliveryUseCase {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DeliveryResponse save(SaveDeliveryRequest request) throws DeliveryInProgressException, OrderNotFoundException {
        if (deliveryRepository.existsByOrderIdAndStatusNot(request.orderId(), DeliveryStatus.CANCELED)) {
            throw new DeliveryInProgressException();
        }

        var order = orderRepository.findById(request.orderId()).orElseThrow(OrderNotFoundException::new);
        order.setStatus(OrderStatus.FINISHED);
        order.setModifiedAt(LocalDateTime.now());
        orderRepository.save(order);

        var delivery = request.toEntity();
        delivery.setOrder(order);
        delivery.setLocation(order.getUser().getAddress());

        var deliverySaved = deliveryRepository.save(delivery);
        return new DeliveryResponse(deliverySaved);
    }
}
