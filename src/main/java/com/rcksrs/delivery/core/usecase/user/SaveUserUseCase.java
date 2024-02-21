package com.rcksrs.delivery.core.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.SaveUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;

public interface SaveUserUseCase {
    UserResponse save(SaveUserRequest request) throws EmailAlreadyExistsException;
}
