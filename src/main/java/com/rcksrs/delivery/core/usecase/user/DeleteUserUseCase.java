package com.rcksrs.delivery.core.usecase.user;

import com.rcksrs.delivery.core.exception.user.UserNotFoundException;

public interface DeleteUserUseCase {
    void delete(Long id) throws UserNotFoundException;
}
