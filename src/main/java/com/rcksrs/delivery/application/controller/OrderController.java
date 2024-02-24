package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.order.*;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.usecase.order.*;
import com.rcksrs.delivery.infra.swagger.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
@SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
public class OrderController {
    private final FindOrderUseCase findOrderUseCase;
    private final SaveOrderUseCase saveOrderUseCase;
    private final SaveNoAccountUserOrderUseCase saveNoAccountUserOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido a partir do id")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findOrderUseCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos a partir do filtro de busca")
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    public ResponseEntity<Page<OrderResponse>> search(OrderFilter filter,
                                                      @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(findOrderUseCase.findByFilter(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "Salvar pedido")
    public ResponseEntity<OrderResponse> save(@RequestBody @Valid SaveOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrderUseCase.save(request));
    }

    @PostMapping("/no-account")
    @Operation(summary = "Salvar pedido para usuário sem conta")
    public ResponseEntity<OrderResponse> saveNoAccountUser(@RequestBody @Valid SaveNoAccountUserOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNoAccountUserOrderUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do pedido")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateOrderRequest request) {
        updateOrderUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        updateOrderUseCase.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOrderUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
