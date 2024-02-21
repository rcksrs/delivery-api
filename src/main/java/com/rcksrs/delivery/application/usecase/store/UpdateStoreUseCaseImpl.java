package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.usecase.store.UpdateStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateStoreUseCaseImpl implements UpdateStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    public void update(Long id, UpdateStoreRequest request) throws StoreNotFoundException, NameAlreadyExistsException {
        var store = storeRepository.findByIdAndActiveTrue(id).orElseThrow(StoreNotFoundException::new);
        var nameExists = storeRepository.findByNameIgnoreCaseAndActiveTrue(request.name()).isPresent();
        if (nameExists) throw new NameAlreadyExistsException();

        var storeUpdated = request.update(store);
        storeRepository.save(storeUpdated);
    }
}
