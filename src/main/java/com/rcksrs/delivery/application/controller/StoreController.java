package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.usecase.store.DeleteStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.FindStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.SaveStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.UpdateStoreUseCase;
import com.rcksrs.delivery.infra.swagger.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/store")
@RequiredArgsConstructor
@Tag(name = "Store Controller")
@SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
public class StoreController {
    private final FindStoreUseCase findStoreUseCase;
    private final SaveStoreUseCase saveStoreUseCase;
    private final UpdateStoreUseCase updateStoreUseCase;
    private final DeleteStoreUseCase deleteStoreUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estabelecimento a partir do id")
    public ResponseEntity<StoreResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findStoreUseCase.findById(id));
    }

    @PostMapping
    @Operation(summary = "Salvar estabelecimento")
    public ResponseEntity<StoreResponse> save(@RequestBody SaveStoreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveStoreUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do estabelecimento")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateStoreRequest request) {
        updateStoreUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar estabelecimento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteStoreUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
