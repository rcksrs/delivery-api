package com.rcksrs.delivery.core.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;

public interface UpdateStoreUseCase {
    void update(Long id, UpdateStoreRequest request) throws StoreNotFoundException, NameAlreadyExistsException;
}
