package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.entity.User;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.UserRepository;
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
class FindUserUseCaseImplTest {
    private static final Long USER_ID = 1L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserUseCaseImpl findUserUseCase;

    @Test
    @DisplayName("Should find user by id when user is saved")
    void shouldFindUserById() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(new User()));
        var response = findUserUseCase.findById(USER_ID);

        assertNotNull(response);
        verify(userRepository).findByIdAndActiveTrue(USER_ID);
    }

    @Test
    @DisplayName("Should not find user by id when user is not saved")
    void shouldNotFindUserById() {
        when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> findUserUseCase.findById(USER_ID));
        verify(userRepository).findByIdAndActiveTrue(USER_ID);
    }

}