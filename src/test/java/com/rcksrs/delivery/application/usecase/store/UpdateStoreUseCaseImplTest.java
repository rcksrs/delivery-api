package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;
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
class UpdateStoreUseCaseImplTest {
    private static final Long STORE_ID = 1L;
    private static final String NAME = "name";
    private static final String ZIP_CODE = "zipCode";

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private UpdateStoreUseCaseImpl updateStoreUseCase;

    @Test
    @DisplayName("Should update store")
    void shouldUpdateStore() {
        var captor = ArgumentCaptor.forClass(Store.class);

        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new Store()));
        when(storeRepository.findByNameIgnoreCaseAndActiveTrue(anyString())).thenReturn(Optional.empty());
        when(storeRepository.save(captor.capture())).thenReturn(new Store());

        var address = new Address();
        address.setZipCode(ZIP_CODE);

        var request = new UpdateStoreRequest(NAME, null, null, null, address);
        updateStoreUseCase.update(STORE_ID, request);

        var storeUpdated = captor.getValue();

        assertNotNull(storeUpdated);
        assertNotNull(storeUpdated.getName());
        assertNotNull(storeUpdated.getCreatedAt());
        assertNotNull(storeUpdated.getModifiedAt());
        assertNotNull(storeUpdated.getAddress());

        assertNull(storeUpdated.getId());
        assertNull(storeUpdated.getEmail());
        assertNull(storeUpdated.getPhone());
        assertNull(storeUpdated.getDescription());

        assertTrue(storeUpdated.isActive());

        assertEquals(NAME, storeUpdated.getName());
        assertEquals(ZIP_CODE, storeUpdated.getAddress().getZipCode());

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(storeRepository).findByNameIgnoreCaseAndActiveTrue(NAME);
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("Should not update store when store was not found")
    void shouldNotUpdateStore() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        var request = new UpdateStoreRequest(NAME, null, null, null, null);

        assertThrows(StoreNotFoundException.class, () -> updateStoreUseCase.update(STORE_ID, request));

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(storeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update store when store name already exists")
    void shouldNotUpdateStoreWhenNameExists() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new Store()));
        when(storeRepository.findByNameIgnoreCaseAndActiveTrue(anyString())).thenReturn(Optional.of(new Store()));

        var request = new UpdateStoreRequest(NAME, null, null, null, null);

        assertThrows(NameAlreadyExistsException.class, () -> updateStoreUseCase.update(STORE_ID, request));

        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
        verify(storeRepository).findByNameIgnoreCaseAndActiveTrue(NAME);
        verify(storeRepository, never()).save(any());
    }

}