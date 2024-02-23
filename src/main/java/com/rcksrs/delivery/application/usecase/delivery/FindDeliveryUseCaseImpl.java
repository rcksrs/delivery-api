package com.rcksrs.delivery.application.usecase.delivery;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryFilter;
import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryResponse;
import com.rcksrs.delivery.core.exception.delivery.DeliveryNotFoundException;
import com.rcksrs.delivery.core.repository.DeliveryRepository;
import com.rcksrs.delivery.core.usecase.delivery.FindDeliveryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindDeliveryUseCaseImpl implements FindDeliveryUseCase {
    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryResponse findById(Long id) throws DeliveryNotFoundException {
        var delivery = deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
        return new DeliveryResponse(delivery);
    }

    @Override
    public Page<DeliveryResponse> findByFilter(DeliveryFilter filter, Pageable pageable) {
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withIgnorePaths("createdAt")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(filter.filter(), matcher);
        return deliveryRepository.findAll(example, pageable).map(DeliveryResponse::new);
    }
}
