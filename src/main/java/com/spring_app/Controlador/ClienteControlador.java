package com.spring_app.Controlador;

import com.spring_app.Entidad.Cliente;
import com.spring_app.Servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteControlador {

    @Autowired
    ClienteServicio clienteServicio;

    //LEER
    @GetMapping("/clientes")
    public String mostrarClientes(@RequestParam(name = "buscarCliente", required = false, defaultValue = "") String buscarCliente, Model model){
        List<Cliente> clientes = clienteServicio.buscarClienteNombre(buscarCliente);
        model.addAttribute("buscarCliente", buscarCliente);
        model.addAttribute("clientes", clientes);
        return "/Cliente/listaClientes";
    }

    //CREAR
    @GetMapping("/formularioCliente")
    public String formularioCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        return "/Cliente/formularioCliente";
    }

    @PostMapping("/guardarCliente")
    public String crearProducto(Cliente cliente){
        clienteServicio.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    //ACTUALIZAR
    @GetMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable Long id, Model model){
        Optional<Cliente> cliente = clienteServicio.buscarCliente(id);
        model.addAttribute("cliente", cliente);
        return "/Cliente/formularioCliente";
    }

    //ELIMINAR
    @GetMapping("/eliminarCliente/{id}")
    public String eliminarCliente(@PathVariable Long id){
        clienteServicio.eliminarCliente(id);
        return "redirect:/clientes";
    }

    /*@GetMapping("/productos/pdf")
    public ResponseEntity<byte[]> descargarPdf() throws Exception{
        String rutaPdf = productoServicio.generarPdf();
        File pdfFile = new File(rutaPdf);
        if (!pdfFile.exists()){
            throw new FileNotFoundException("El archivo pdf no existe");
        }
        byte[] contenido = Files.readAllBytes(pdfFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment","productos.pdf");
        return  new ResponseEntity<>(contenido, headers, HttpStatus.OK);
    }*/
}