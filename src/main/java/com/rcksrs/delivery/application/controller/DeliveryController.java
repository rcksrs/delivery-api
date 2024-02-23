package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryFilter;
import com.rcksrs.delivery.core.domain.dto.delivery.DeliveryResponse;
import com.rcksrs.delivery.core.domain.dto.delivery.SaveDeliveryRequest;
import com.rcksrs.delivery.core.domain.dto.delivery.UpdateDeliveryRequest;
import com.rcksrs.delivery.core.domain.entity.DeliveryStatus;
import com.rcksrs.delivery.core.exception.global.ExceptionMessage;
import com.rcksrs.delivery.core.usecase.delivery.DeleteDeliveryUseCase;
import com.rcksrs.delivery.core.usecase.delivery.FindDeliveryUseCase;
import com.rcksrs.delivery.core.usecase.delivery.SaveDeliveryUseCase;
import com.rcksrs.delivery.core.usecase.delivery.UpdateDeliveryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery Controller")
public class DeliveryController {
    private final FindDeliveryUseCase findDeliveryUseCase;
    private final SaveDeliveryUseCase saveDeliveryUseCase;
    private final UpdateDeliveryUseCase updateDeliveryUseCase;
    private final DeleteDeliveryUseCase deleteDeliveryUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrega a partir do id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<DeliveryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findDeliveryUseCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar entregas a partir do filtro de busca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    public ResponseEntity<Page<DeliveryResponse>> search(DeliveryFilter filter,
                                                         @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(findDeliveryUseCase.findByFilter(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "Salvar entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
    })
    public ResponseEntity<DeliveryResponse> save(@RequestBody SaveDeliveryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDeliveryUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações da entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateDeliveryRequest request) {
        updateDeliveryUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody DeliveryStatus status) {
        updateDeliveryUseCase.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteDeliveryUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
