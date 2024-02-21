package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.store.SaveStoreRequest;
import com.rcksrs.delivery.core.domain.dto.store.StoreResponse;
import com.rcksrs.delivery.core.domain.dto.store.UpdateStoreRequest;
import com.rcksrs.delivery.core.exception.global.ExceptionMessage;
import com.rcksrs.delivery.core.usecase.store.DeleteStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.FindStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.SaveStoreUseCase;
import com.rcksrs.delivery.core.usecase.store.UpdateStoreUseCase;
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
@RequestMapping(value = "/v1/store")
@RequiredArgsConstructor
@Tag(name = "Store Controller")
public class StoreController {
    private final FindStoreUseCase findStoreUseCase;
    private final SaveStoreUseCase saveStoreUseCase;
    private final UpdateStoreUseCase updateStoreUseCase;
    private final DeleteStoreUseCase deleteStoreUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estabelecimento a partir do id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<StoreResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findStoreUseCase.findById(id));
    }

    @PostMapping
    @Operation(summary = "Salvar estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
    })
    public ResponseEntity<StoreResponse> save(@RequestBody SaveStoreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveStoreUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateStoreRequest request) {
        updateStoreUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteStoreUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
