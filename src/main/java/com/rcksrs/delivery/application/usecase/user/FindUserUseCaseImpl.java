package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.UserRepository;
import com.rcksrs.delivery.core.usecase.user.FindUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserUseCaseImpl implements FindUserUseCase {
    private final UserRepository userRepository;

    @Override
    public UserResponse findById(Long id) throws UserNotFoundException {
        var user = userRepository.findByIdAndActiveTrue(id).orElseThrow(UserNotFoundException::new);
        return new UserResponse(user);
    }
}
