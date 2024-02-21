package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.UpdateUserRequest;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.UserRepository;
import com.rcksrs.delivery.core.usecase.user.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void update(Long id, UpdateUserRequest request) throws UserNotFoundException {
        var user = userRepository.findByIdAndActiveTrue(id).orElseThrow(UserNotFoundException::new);
        var userUpdated = request.update(user);
        userRepository.save(userUpdated);
    }

    @Override
    public void updateRole(Long id, Role role) throws UserNotFoundException {
        var user = userRepository.findByIdAndActiveTrue(id).orElseThrow(UserNotFoundException::new);
        user.setRole(role);
        user.setModifiedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void updateEmail(Long id, String email) throws UserNotFoundException, EmailAlreadyExistsException {
        var user = userRepository.findByIdAndActiveTrue(id).orElseThrow(UserNotFoundException::new);
        var emailExists = userRepository.findByEmailAndActiveTrue(email).isPresent();
        if (emailExists) throw new EmailAlreadyExistsException();

        user.setEmail(email);
        user.setModifiedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
