package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.entity.User;
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
class DeleteUserUseCaseImplTest {
    private static final Long USER_ID = 1L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    @DisplayName("Should delete user")
    void shouldDeleteUser() {
        var captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(captor.capture())).thenReturn(new User());

        deleteUserUseCase.delete(USER_ID);
        var userDeleted = captor.getValue();

        assertNotNull(userDeleted);
        assertNotNull(userDeleted.getModifiedAt());
        assertFalse(userDeleted.isActive());

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not delete user when user was not found")
    void shouldNotDeleteUser() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.delete(USER_ID));

        verify(userRepository).findByIdAndActiveTrue(USER_ID);
        verify(userRepository, never()).save(any());
    }

}