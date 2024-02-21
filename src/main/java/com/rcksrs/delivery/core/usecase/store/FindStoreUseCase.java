package com.rcksrs.delivery.core.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;

public interface FindStoreUseCase {
    StoreResponse findById(Long id) throws StoreNotFoundException;
}
