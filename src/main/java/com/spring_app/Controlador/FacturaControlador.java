package com.spring_app.Controlador;

import com.spring_app.Entidad.Factura;
import com.spring_app.Servicio.FacturaServicio;
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

    //Leer
    @GetMapping("/facturas")
    public String mostrarFacturas(@RequestParam(name = "buscarFactura", required = false, defaultValue = "") String buscarFactura, Model model) {
        List<Factura> facturas = facturaServicio.listarFacturas();
        model.addAttribute("facturas", facturas);
        return "/Factura/listaFacturas";
    }

    //CREAR
    @GetMapping("/formularioFactura")
    public String formularioFactura(Model model){
        model.addAttribute("factura", new Factura());
        return "/Factura/formularioFactura";

    }

    @PostMapping("/guardarFactura")
    public String crearFactura(Factura factura){
        facturaServicio.guardarFactura(factura);
        return "redirect:/facturas";
    }

    //ACTUALIZAR
    @GetMapping("/editarFactura/{id}")
    public String editarFactura(@PathVariable Long id, Model model){
        Optional<Factura> factura = facturaServicio.buscarFactura(id);
        model.addAttribute("factura", factura);
        return "/Factura/formularioFactura";
    }

    //ELIMINAR
    @GetMapping("/eliminarFactura/{id}")
    public String eliminarFactura(@PathVariable Long id){
        facturaServicio.eliminarFactura(id);
        return "redirect:/facturas";
    }
}
