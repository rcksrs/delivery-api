package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.user.AuthUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.AuthUserResponse;
import com.rcksrs.delivery.infra.security.TokenService;
import com.rcksrs.delivery.infra.security.UserDetailsEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    @Operation(summary = "Gerar token de acesso")
    public ResponseEntity<AuthUserResponse> token(@RequestBody AuthUserRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(authentication);
        var token = tokenService.generate((UserDetailsEntity) auth.getPrincipal());

        return ResponseEntity.ok(new AuthUserResponse(token));
    }
}
