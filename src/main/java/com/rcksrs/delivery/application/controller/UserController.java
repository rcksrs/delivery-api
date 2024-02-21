package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.user.SaveUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UpdateUserRequest;
import com.rcksrs.delivery.core.domain.dto.user.UserResponse;
import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.exception.global.ExceptionMessage;
import com.rcksrs.delivery.core.usecase.user.DeleteUserUseCase;
import com.rcksrs.delivery.core.usecase.user.FindUserUseCase;
import com.rcksrs.delivery.core.usecase.user.SaveUserUseCase;
import com.rcksrs.delivery.core.usecase.user.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findUserUseCase.findById(id));
    }

    @PostMapping
    @Operation(summary = "Salvar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
    })
    public ResponseEntity<UserResponse> save(@RequestBody SaveUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações básicas do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        updateUserUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/email")
    @Operation(summary = "Atualizar email do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody String email) {
        updateUserUseCase.updateEmail(id, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Atualizar perfil do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> updateRole(@PathVariable Long id, @RequestBody Role role) {
        updateUserUseCase.updateRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
