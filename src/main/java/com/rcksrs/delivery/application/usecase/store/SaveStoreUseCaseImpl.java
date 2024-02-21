package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import com.rcksrs.delivery.core.usecase.store.SaveStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveStoreUseCaseImpl implements SaveStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    public StoreResponse save(SaveStoreRequest request) throws NameAlreadyExistsException {
        var nameExists = storeRepository.findByNameIgnoreCaseAndActiveTrue(request.name()).isPresent();
        if (nameExists) throw new NameAlreadyExistsException();

        var storeSaved = storeRepository.save(request.toEntity());
        return new StoreResponse(storeSaved);
    }
}
