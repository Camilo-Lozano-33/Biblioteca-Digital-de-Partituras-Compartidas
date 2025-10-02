package com.BibliotecaPartituras.biblioteca.partituras.app.controller;

import com.BibliotecaPartituras.biblioteca.partituras.app.model.Partitura;
import com.BibliotecaPartituras.biblioteca.partituras.app.model.Usuario;
import com.BibliotecaPartituras.biblioteca.partituras.app.repository.PartituraRepository;
import com.BibliotecaPartituras.biblioteca.partituras.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.security.Principal;

@Controller
@RequestMapping("/partituras")
public class PartituraController {

    @Autowired
    private PartituraRepository partituraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarPartituras(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Partitura> partituras = partituraRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("partituras", partituras);
        return "partituras/listado";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("partitura", new Partitura());
        return "partituras/formulario";
    }

    @PostMapping("/nueva")
    public String subirPartitura(@ModelAttribute Partitura partitura,
                                 @RequestParam("archivo") MultipartFile archivo,
                                 Principal principal,
                                 Model model) throws Exception {
        if (archivo.isEmpty() || !archivo.getOriginalFilename().endsWith(".pdf") || archivo.getSize() > 10_000_000) {
            model.addAttribute("error", "Archivo inválido. Debe ser PDF y menor a 10MB.");
            return "partituras/formulario";
        }
        if (partituraRepository.existsByTituloAndCompositorAndInstrumento(
                partitura.getTitulo(), partitura.getCompositor(), partitura.getInstrumento())) {
            model.addAttribute("error", "Ya existe una partitura con esos datos.");
            return "partituras/formulario";
        }
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        Path ruta = Paths.get("uploads").resolve(nombreArchivo);
        Files.createDirectories(ruta.getParent());
        archivo.transferTo(ruta);

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElseThrow();
        partitura.setArchivoPdf(nombreArchivo);
        partitura.setUsuario(usuario);
        partituraRepository.save(partitura);
        return "redirect:/partituras";
    }
}
