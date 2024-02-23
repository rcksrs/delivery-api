package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.exception.store.NameAlreadyExistsException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveStoreUseCaseImplTest {
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String DESCRIPTION = "description";
    private static final String ZIP_CODE = "zipCode";

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private SaveStoreUseCaseImpl saveStoreUseCase;

    @Test
    @DisplayName("Should save new store")
    void shouldSaveStore() {
        var captor = ArgumentCaptor.forClass(Store.class);

        when(storeRepository.findByNameIgnoreCaseAndActiveTrue(anyString())).thenReturn(Optional.empty());
        when(storeRepository.save(captor.capture())).thenReturn(new Store());

        var address = new Address();
        address.setZipCode(ZIP_CODE);

        var request = new SaveStoreRequest(NAME, EMAIL, PHONE, DESCRIPTION, address);
        var response = saveStoreUseCase.save(request);
        var storeSaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(storeSaved);
        assertNotNull(storeSaved.getCreatedAt());

        assertNull(storeSaved.getId());
        assertNull(storeSaved.getModifiedAt());

        assertTrue(storeSaved.isActive());

        assertEquals(NAME, storeSaved.getName());
        assertEquals(EMAIL, storeSaved.getEmail());
        assertEquals(DESCRIPTION, storeSaved.getDescription());
        assertEquals(PHONE, storeSaved.getPhone());
        assertEquals(ZIP_CODE, storeSaved.getAddress().getZipCode());

        verify(storeRepository).findByNameIgnoreCaseAndActiveTrue(NAME);
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("Should not save new store when store name already exists")
    void shouldNotSaveStore() {
        when(storeRepository.findByNameIgnoreCaseAndActiveTrue(anyString())).thenReturn(Optional.of(new Store()));

        var request = new SaveStoreRequest(NAME, EMAIL, PHONE, DESCRIPTION, new Address());
        assertThrows(NameAlreadyExistsException.class, () -> saveStoreUseCase.save(request));

        verify(storeRepository).findByNameIgnoreCaseAndActiveTrue(NAME);
        verify(storeRepository, never()).save(any());
    }

}