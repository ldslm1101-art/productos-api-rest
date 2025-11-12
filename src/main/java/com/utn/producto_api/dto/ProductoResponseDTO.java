package com.utn.producto_api.dto;

import com.utn.producto_api.model.Categoria;
import lombok.Data;
@Data

public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Categoria categoria;
}
