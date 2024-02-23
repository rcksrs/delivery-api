package com.rcksrs.delivery.application.controller;

import com.rcksrs.delivery.core.domain.dto.order.OrderFilter;
import com.rcksrs.delivery.core.domain.dto.order.OrderResponse;
import com.rcksrs.delivery.core.domain.dto.order.SaveOrderRequest;
import com.rcksrs.delivery.core.domain.dto.order.UpdateOrderRequest;
import com.rcksrs.delivery.core.domain.entity.OrderStatus;
import com.rcksrs.delivery.core.exception.global.ExceptionMessage;
import com.rcksrs.delivery.core.usecase.order.DeleteOrderUseCase;
import com.rcksrs.delivery.core.usecase.order.FindOrderUseCase;
import com.rcksrs.delivery.core.usecase.order.SaveOrderUseCase;
import com.rcksrs.delivery.core.usecase.order.UpdateOrderUseCase;
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
@RequestMapping(value = "/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {
    private final FindOrderUseCase findOrderUseCase;
    private final SaveOrderUseCase saveOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido a partir do id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findOrderUseCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos a partir do filtro de busca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
    })
    public ResponseEntity<OrderResponse> save(@RequestBody SaveOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrderUseCase.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateOrderRequest request) {
        updateOrderUseCase.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        updateOrderUseCase.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionMessage.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOrderUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
