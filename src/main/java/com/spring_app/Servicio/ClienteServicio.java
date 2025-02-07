
package com.spring_app.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring_app.Entidad.Cliente;
import com.spring_app.Entidad.Producto;
import com.spring_app.Repositorio.ClienteRepositorio;
import com.spring_app.Repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {
    @Autowired
    ClienteRepositorio clienteRepositorio;

    public List<Cliente> listarClientes() {

        return clienteRepositorio.findAll();
    }

    public List<Cliente> buscarClienteNombre(String buscarCliente) {
        if (buscarCliente == null || buscarCliente.isEmpty()) {
            return clienteRepositorio.findAll();
        } else {
            return clienteRepositorio.findByNombreContainingIgnoreCase(buscarCliente);
        }
    }

    public Optional<Cliente> buscarCliente(Long id) {

        return clienteRepositorio.findById(id);
    }

    public void guardarCliente(Cliente cliente) {

        clienteRepositorio.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
    }
}
