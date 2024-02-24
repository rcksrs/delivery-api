package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.user.SaveUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UpdateUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.usecase.user.DeleteUserUseCase;
import com.rcksrs.delivery.core.usecase.user.FindUserUseCase;
import com.rcksrs.delivery.core.usecase.user.SaveUserUseCase;
import com.rcksrs.delivery.core.usecase.user.UpdateUserUseCase;
import com.rcksrs.delivery.infra.swagger.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final FindUserUseCase findUserUseCase;
    private final SaveUserUseCase saveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário a partir do id")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findUserUseCase.findById(id));
    }

    @PostMapping
    @Operation(summary = "Salvar usuário")
    public ResponseEntity<UserResponse> save(@RequestBody @Valid SaveUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações básicas do usuário")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        updateUserUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/email")
    @Operation(summary = "Atualizar email do usuário")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody String email) {
        updateUserUseCase.updateEmail(id, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Atualizar perfil do usuário")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
    public ResponseEntity<Void> updateRole(@PathVariable Long id, @RequestBody Role role) {
        updateUserUseCase.updateRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar usuário")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
