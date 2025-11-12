package com.utn.producto_api.service;

import com.utn.producto_api.dto.ProductoDTO;
import com.utn.producto_api.dto.ProductoResponseDTO;
import com.utn.producto_api.exception.ProductoNotFoundException;
import com.utn.producto_api.model.Producto;
import com.utn.producto_api.model.Categoria;
import com.utn.producto_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductoService {
    private final ProductoRepository productoRepository;
    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = convertirAProducto(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return convertirAProductoResponseDTO(productoGuardado);
    }

    public List<ProductoResponseDTO> obtenerTodos() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .map(this::convertirAProductoResponseDTO)
                .toList();
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::convertirAProductoResponseDTO);
    }

    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(this::convertirAProductoResponseDTO)
                .toList();
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    // Actualizamos la entidad existente con datos del DTO
                    productoExistente.setNombre(productoDTO.getNombre());
                    productoExistente.setDescripcion(productoDTO.getDescripcion());
                    productoExistente.setPrecio(productoDTO.getPrecio());
                    productoExistente.setStock(productoDTO.getStock());
                    productoExistente.setCategoria(productoDTO.getCategoria());

                    Producto productoGuardado = productoRepository.save(productoExistente);
                    return convertirAProductoResponseDTO(productoGuardado);
                })
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }
    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setStock(nuevoStock);
                    Producto productoGuardado = productoRepository.save(productoExistente);
                    return convertirAProductoResponseDTO(productoGuardado); // Devuelve DTO
                })
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }
    public void eliminarProducto(Long id){
        if(!productoRepository.existsById(id)){
            throw new RuntimeException("Producto no encontrado con id: "+id);
        }
        productoRepository.deleteById(id);
    }
    private ProductoResponseDTO convertirAProductoResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        return dto;
    }
    private Producto convertirAProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        return producto;
    }


}
