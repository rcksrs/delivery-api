package com.rcksrs.delivery.infra.security;

import com.rcksrs.delivery.core.exception.user.UserNotFoundException;
import com.rcksrs.delivery.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        var user = userRepository.findByEmailAndActiveTrue(username).orElseThrow(UserNotFoundException::new);
        return new UserDetailsEntity(user);
    }
}
