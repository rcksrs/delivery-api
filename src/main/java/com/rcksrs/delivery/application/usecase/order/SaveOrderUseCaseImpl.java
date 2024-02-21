package com.rcksrs.delivery.application.usecase.order;

import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.domain.dto.order.SaveOrderRequest;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.OrderRepository;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.repository.UserRepository;
import com.rcksrs.delivery.core.usecase.order.SaveOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveOrderUseCaseImpl implements SaveOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public OrderResponse save(SaveOrderRequest request) throws UserNotFoundException, StoreNotFoundException {
        var user = userRepository.findByIdAndActiveTrue(request.userId()).orElseThrow(UserNotFoundException::new);
        var store = storeRepository.findByIdAndActiveTrue(request.storeId()).orElseThrow(StoreNotFoundException::new);

        var order = request.toEntity();
        order.setUser(user);
        order.setStore(store);

        var orderSaved = orderRepository.save(order);
        return new OrderResponse(orderSaved);
    }
}
