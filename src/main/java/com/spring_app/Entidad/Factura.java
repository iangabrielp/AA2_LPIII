package com.spring_app.Entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private Double precio;
    private Double subtotal;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    // Este campo lo AGREGAS con las anotaciones
    @ManyToMany
    @JoinTable(
            name = "factura_producto",
            joinColumns = @JoinColumn(name = "factura_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

}
