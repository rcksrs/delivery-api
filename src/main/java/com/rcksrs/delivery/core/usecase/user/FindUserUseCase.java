package com.rcksrs.delivery.core.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;

public interface FindUserUseCase {
    UserResponse findById(Long id) throws UserNotFoundException;
    UserResponse findByEmailAndPassword(String email, String password) throws UserNotFoundException;
}
