package com.utn.producto_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ActualizarStockDTO {
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value=0, message = "El stock no puede ser negativo")
    private Integer stock;
}
