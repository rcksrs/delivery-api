package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.usecase.store.DeleteStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteStoreUseCaseImpl implements DeleteStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    public void delete(Long id) throws StoreNotFoundException {
        var store = storeRepository.findByIdAndActiveTrue(id).orElseThrow(StoreNotFoundException::new);
        store.setActive(false);
        store.setModifiedAt(LocalDateTime.now());

        storeRepository.save(store);
    }
}
