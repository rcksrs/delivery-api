package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.SaveUserRequest;
import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;
import com.rcksrs.delivery.core.repository.UserRepository;
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
class SaveUserUseCaseImplTest {
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String ZIP_CODE = "zipCode";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaveUserUseCaseImpl saveUserUseCase;

    @Test
    @DisplayName("Should save new user")
    void shouldSaveUser() {
        var captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByEmailAndActiveTrue(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(captor.capture())).thenReturn(new User());

        var address = new Address();
        address.setZipCode(ZIP_CODE);

        var request = new SaveUserRequest(NAME, EMAIL, PASSWORD, PHONE, address);
        var response = saveUserUseCase.save(request);
        var userSaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(userSaved);
        assertNotNull(userSaved.getCreatedAt());

        assertNull(userSaved.getId());
        assertNull(userSaved.getModifiedAt());

        assertTrue(userSaved.isActive());
        assertEquals(Role.USER, userSaved.getRole());

        assertEquals(NAME, userSaved.getName());
        assertEquals(EMAIL, userSaved.getEmail());
        assertEquals(PASSWORD, userSaved.getPassword());
        assertEquals(PHONE, userSaved.getPhone());
        assertEquals(ZIP_CODE, userSaved.getAddress().getZipCode());

        verify(userRepository).findByEmailAndActiveTrue(EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not save new user when user email already exists")
    void shouldNotSaveUser() {
        when(userRepository.findByEmailAndActiveTrue(anyString())).thenReturn(Optional.of(new User()));

        var request = new SaveUserRequest(NAME, EMAIL, PASSWORD, PHONE, new Address());
        assertThrows(EmailAlreadyExistsException.class, () -> saveUserUseCase.save(request));

        verify(userRepository).findByEmailAndActiveTrue(EMAIL);
        verify(userRepository, never()).save(any());
    }

}