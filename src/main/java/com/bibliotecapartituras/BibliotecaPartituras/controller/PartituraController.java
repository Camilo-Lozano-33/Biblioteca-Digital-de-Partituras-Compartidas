package com.bibliotecapartituras.BibliotecaPartituras.controller;

import com.bibliotecapartituras.BibliotecaPartituras.model.Usuario;
import com.bibliotecapartituras.BibliotecaPartituras.service.PartituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PartituraController {
    @Autowired
    private PartituraService partituraService;

    @GetMapping("/partituras/subir")
    public String mostrarFormularioSubida(Model model) {
        model.addAttribute("partituraForm", new PartituraForm());
        return "subir_partitura";
    }

    @PostMapping("/partituras/subir")
    public String subirPartitura(@ModelAttribute PartituraForm partituraForm,
                                 @RequestParam("archivoPdf") MultipartFile archivoPdf,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        try {
            // Aquí deberías obtener el usuario real desde la base de datos usando el email
            Usuario usuario = new Usuario();
            usuario.setEmail(userDetails.getUsername());
            // En un sistema real, busca el usuario por email y pásalo
            partituraService.guardarPartitura(
                partituraForm.getTitulo(),
                partituraForm.getCompositor(),
                partituraForm.getInstrumento(),
                partituraForm.getGenero(),
                partituraForm.getDificultad(),
                archivoPdf,
                usuario
            );
            model.addAttribute("exito", "Partitura subida correctamente");
        } catch (Exception e) {
            model.addAttribute("error", "Error al subir la partitura: " + e.getMessage());
        }
        return "subir_partitura";
    }

    public static class PartituraForm {
        private String titulo;
        private String compositor;
        private String instrumento;
        private String genero;
        private String dificultad;
        // Getters y setters
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public String getCompositor() { return compositor; }
        public void setCompositor(String compositor) { this.compositor = compositor; }
        public String getInstrumento() { return instrumento; }
        public void setInstrumento(String instrumento) { this.instrumento = instrumento; }
        public String getGenero() { return genero; }
        public void setGenero(String genero) { this.genero = genero; }
        public String getDificultad() { return dificultad; }
        public void setDificultad(String dificultad) { this.dificultad = dificultad; }
    }
}
