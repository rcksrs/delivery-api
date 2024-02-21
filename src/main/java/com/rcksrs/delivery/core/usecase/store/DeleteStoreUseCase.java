package com.rcksrs.delivery.core.usecase.store;

import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;

public interface DeleteStoreUseCase {
    void delete(Long id) throws StoreNotFoundException;
}
