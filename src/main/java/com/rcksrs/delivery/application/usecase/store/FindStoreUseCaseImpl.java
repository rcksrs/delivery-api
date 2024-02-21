package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.usecase.store.FindStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStoreUseCaseImpl implements FindStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    public StoreResponse findById(Long id) throws StoreNotFoundException {
        var store = storeRepository.findByIdAndActiveTrue(id).orElseThrow(StoreNotFoundException::new);
        return new StoreResponse(store);
    }
}
