package com.BibliotecaPartituras.biblioteca.partituras.app.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USUARIO;

    public enum Rol { USUARIO, ADMINISTRADOR }

    // Getters y setters
    public Long getId() { return id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
