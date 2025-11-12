package com.utn.producto_api.dto;

import com.utn.producto_api.model.Categoria;
import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para crear o actualizar un producto (sin ID)")
public class ProductoDTO {

    @Schema(description = "Nombre del producto", example = "Teclado Mecánico RGB", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message="El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 carácteres")
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Switch Blue, 104 teclas, español")
    @Size(max= 500, message ="La descripción no puede exceder los 500 carácteres")
    private String descripcion;

    @Schema(description = "Precio unitario del producto", example = "89.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser un valor positivo")
    @Min (value=0, message = "El precio debe ser como mínimo 0")
    private Double precio;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "La categoría no puede ser nula")
    private Categoria categoria;
}
