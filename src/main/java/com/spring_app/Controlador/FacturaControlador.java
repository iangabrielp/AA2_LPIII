package com.spring_app.Controlador;

import com.spring_app.Entidad.Cliente;
import com.spring_app.Entidad.Factura;
import com.spring_app.Entidad.Producto;
import com.spring_app.Servicio.ClienteServicio;
import com.spring_app.Servicio.FacturaServicio;
import com.spring_app.Servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class FacturaControlador {

    @Autowired
    FacturaServicio facturaServicio;
    @Autowired
    ClienteServicio clienteServicio;
    @Autowired
    ProductoServicio productoServicio;

    // ... (otros métodos)

    @GetMapping("/generarFactura")
    public String mostrarFormularioFactura(Model model) {
        List<Cliente> clientes = clienteServicio.listarClientes();
        List<Producto> productos = productoServicio.listarProductos();
        model.addAttribute("clientes", clientes);
        model.addAttribute("productos", productos);
        return "/Factura/generarFactura";
    }

    @PostMapping("/guardarFactura")
    public String guardarFactura(@RequestParam("clienteId") Long clienteId,
                                 @RequestParam("productoIds") List<Long> productoIds,
                                 @RequestParam("cantidades") List<Integer> cantidades,
                                 Model model) {
        Cliente cliente = clienteServicio.buscarCliente(clienteId).orElse(null);
        List<Producto> productos = productoServicio.listarProductosPorIds(productoIds);

        if (cliente == null || productos == null || productos.isEmpty()) {

            return "redirect:/generarFactura?error=1";
        }

        double subtotal = 0;
        for (int i = 0; i < productos.size(); i++) {
            subtotal += productos.get(i).getPrecio() * cantidades.get(i);
        }
        // calcular impuestos, descuentos, etc.
        double total = subtotal;

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setProductos(productos);
        factura.setCantidad(1);
        factura.setPrecio(subtotal);
        factura.setSubtotal(subtotal);
        factura.setTotal(total);

        facturaServicio.guardarFactura(factura);

        return "redirect:/factura/" + factura.getId();
    }

}
