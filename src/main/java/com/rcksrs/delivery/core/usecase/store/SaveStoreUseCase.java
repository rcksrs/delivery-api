package com.rcksrs.delivery.core.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;

public interface SaveStoreUseCase {
    StoreResponse save(SaveStoreRequest request) throws NameAlreadyExistsException;
}
