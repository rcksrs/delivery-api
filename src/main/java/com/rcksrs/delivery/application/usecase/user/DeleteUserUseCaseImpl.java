package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.UserRepository;
import com.rcksrs.delivery.core.usecase.user.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void delete(Long id) throws UserNotFoundException {
        var user = userRepository.findByIdAndActiveTrue(id).orElseThrow(UserNotFoundException::new);
        user.setActive(false);
        user.setModifiedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
