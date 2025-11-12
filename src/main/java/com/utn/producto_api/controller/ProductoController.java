package com.utn.producto_api.controller;

import com.utn.producto_api.dto.ActualizarStockDTO;
import com.utn.producto_api.dto.ProductoDTO;
import com.utn.producto_api.dto.ProductoResponseDTO;
import com.utn.producto_api.model.Categoria;
import com.utn.producto_api.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Gestión de Productos", description = "API para operaciones CRUD de productos")
public class ProductoController {
    private final ProductoService productoService;
    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Error de validación)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) { // 6. @Valid y @RequestBody
        ProductoResponseDTO productoCreado = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED); // 7. Respuesta 201
    }


    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK); // 8. Respuesta 200
    }


    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado (ID no existe)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorId(@PathVariable Long id) { // 9. @PathVariable
        return productoService.obtenerPorId(id)
                .map(producto -> new ResponseEntity<>(producto, HttpStatus.OK)) // Si lo encuentra, 200 OK
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no, 404 NOT_FOUND
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        // (Nota: La lógica de si existe o no ya la maneja tu servicio y lanza excepción)
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }


    @PatchMapping("/{id}/stock") // 10. @PatchMapping
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO stockDTO) {
        ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, stockDTO.getStock());
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 11. Respuesta 204
    }
}
