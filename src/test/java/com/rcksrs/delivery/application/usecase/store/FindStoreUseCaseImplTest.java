package com.rcksrs.delivery.application.usecase.store;

import com.rcksrs.delivery.core.domain.entity.Store;
import com.rcksrs.delivery.core.exception.store.StoreNotFoundException;
import com.rcksrs.delivery.core.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindStoreUseCaseImplTest {
    private static final Long STORE_ID = 1L;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private FindStoreUseCaseImpl findStoreUseCase;

    @Test
    @DisplayName("Should find store by id when store is saved")
    void shouldFindStoreById() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new Store()));
        var response = findStoreUseCase.findById(STORE_ID);

        assertNotNull(response);
        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
    }

    @Test
    @DisplayName("Should not find store by id when store is not saved")
    void shouldNotFindStoreById() {
        when(storeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class, () -> findStoreUseCase.findById(STORE_ID));
        verify(storeRepository).findByIdAndActiveTrue(STORE_ID);
    }

}