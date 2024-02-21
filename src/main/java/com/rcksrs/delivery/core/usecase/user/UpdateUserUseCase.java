package com.rcksrs.delivery.core.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.UpdateUserRequest;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;

public interface UpdateUserUseCase {
    void update(Long id, UpdateUserRequest request) throws UserNotFoundException;
    void updateRole(Long id, Role role) throws UserNotFoundException;
    void updateEmail(Long id, String email) throws UserNotFoundException, EmailAlreadyExistsException;
}
