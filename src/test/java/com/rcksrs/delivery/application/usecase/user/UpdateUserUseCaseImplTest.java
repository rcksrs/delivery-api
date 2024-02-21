package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.UpdateUserRequest;
import com.rcksrs.delivery.core.domain.entity.Address;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {
    private static final Long USER_ID = 1L;
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String ZIP_CODE = "zipCode";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    @DisplayName("Should update user")
    void shouldUpdateUser() {
        var captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(captor.capture())).thenReturn(new User());

        var address = new Address();
        address.setZipCode(ZIP_CODE);

        var request = new UpdateUserRequest(NAME, null, null, address);
        updateUserUseCase.update(USER_ID, request);

        var userUpdated = captor.getValue();

        assertNotNull(userUpdated);
        assertNotNull(userUpdated.getCreatedAt());
        assertNotNull(userUpdated.getModifiedAt());
        assertNotNull(userUpdated.getAddress());

        assertNull(userUpdated.getId());
        assertNull(userUpdated.getEmail());
        assertNull(userUpdated.getPhone());
        assertNull(userUpdated.getPassword());
        assertNull(userUpdated.getRole());

        assertTrue(userUpdated.isActive());

        assertEquals(NAME, userUpdated.getName());
        assertEquals(ZIP_CODE, userUpdated.getAddress().getZipCode());

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user when user was not found")
    void shouldNotUpdateUser() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        var request = new UpdateUserRequest(NAME, null, null, null);

        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.update(USER_ID, request));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update user role")
    void shouldUpdateUserRole() {
        var captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(captor.capture())).thenReturn(new User());

        updateUserUseCase.updateRole(USER_ID, Role.ADMIN);
        var userUpdated = captor.getValue();

        assertNotNull(userUpdated);
        assertNotNull(userUpdated.getRole());
        assertNotNull(userUpdated.getModifiedAt());

        assertEquals(Role.ADMIN, userUpdated.getRole());

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user role when user was not found")
    void shouldNotUpdateUserRole() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.updateRole(USER_ID, Role.ADMIN));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update user email")
    void shouldUpdateUserEmail() {
        var captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmailAndActiveTrue(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(captor.capture())).thenReturn(new User());

        updateUserUseCase.updateEmail(USER_ID, EMAIL);
        var userUpdated = captor.getValue();

        assertNotNull(userUpdated);
        assertNotNull(userUpdated.getEmail());
        assertNotNull(userUpdated.getModifiedAt());

        assertEquals(EMAIL, userUpdated.getEmail());

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository).findByEmailAndActiveTrue(EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user email when user was not found")
    void shouldNotUpdateUserEmailWhenUserWasNotFound() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.updateEmail(USER_ID, EMAIL));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository, never()).findByEmailAndActiveTrue(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update user email when email already exists")
    void shouldNotUpdateUserEmailWhenEmailAlreadyExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmailAndActiveTrue(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> updateUserUseCase.updateEmail(USER_ID, EMAIL));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository).findByEmailAndActiveTrue(EMAIL);
        verify(userRepository, never()).save(any());
    }

}