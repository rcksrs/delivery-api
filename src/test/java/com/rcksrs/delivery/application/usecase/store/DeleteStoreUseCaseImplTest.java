package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteStoreUseCaseImplTest {
    private static final Long STORE_ID = 1L;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DeleteStoreUseCaseImpl deleteStoreUseCase;

    @Test
    @DisplayName("Should delete store")
    void shouldDeleteStore() {
        var captor = ArgumentCaptor.forClass(Store.class);

        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new Store()));
        when(storeRepository.save(captor.capture())).thenReturn(new Store());

        deleteStoreUseCase.delete(STORE_ID);
        var storeDeleted = captor.getValue();

        assertNotNull(storeDeleted);
        assertNotNull(storeDeleted.getModifiedAt());
        assertFalse(storeDeleted.isActive());

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("Should not delete store when store was not found")
    void shouldNotDeleteStore() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class, () -> deleteStoreUseCase.delete(STORE_ID));

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(storeRepository, never()).save(any());
    }

}