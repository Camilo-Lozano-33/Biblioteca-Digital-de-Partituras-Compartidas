package com.bibliotecapartituras.BibliotecaPartituras.controller;

import com.bibliotecapartituras.BibliotecaPartituras.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new RegistroRequest());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") RegistroRequest request, Model model) {
        if (usuarioService.existeEmail(request.getEmail())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }
        usuarioService.registrarUsuario(request.getEmail(), request.getPassword());
        model.addAttribute("exito", "Usuario registrado correctamente. Inicia sesión.");
        return "login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    // DTO para el formulario de registro
    public static class RegistroRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
