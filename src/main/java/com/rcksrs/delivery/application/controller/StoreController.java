package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.usecase.store.DeleteStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.FindStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.SaveStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.UpdateStoreUseCase;
import com.rcksrs.delivery.infra.role.RequiresManagerRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/store")
@RequiredArgsConstructor
@Tag(name = "Store Controller")
@RequiresManagerRole
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StoreResponse> save(@RequestBody @Valid SaveStoreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveStoreUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do estabelecimento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateStoreRequest request) {
        updateStoreUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar estabelecimento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteStoreUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
