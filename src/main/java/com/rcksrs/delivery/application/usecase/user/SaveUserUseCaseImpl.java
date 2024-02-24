package com.rcksrs.delivery.application.usecase.user;

import com.rcksrs.delivery.core.domain.dto.user.SaveUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.exception.user.EmailAlreadyExistsException;
import com.rcksrs.delivery.core.repository.UserRepository;
import com.rcksrs.delivery.core.usecase.user.SaveUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveUserUseCaseImpl implements SaveUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(SaveUserRequest request) throws EmailAlreadyExistsException {
        var emailExists = userRepository.findByEmailAndActiveTrue(request.email()).isPresent();
        if (emailExists) throw new EmailAlreadyExistsException();

        var user = request.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var userSaved = userRepository.save(user);
        return new UserResponse(userSaved);
    }
}
