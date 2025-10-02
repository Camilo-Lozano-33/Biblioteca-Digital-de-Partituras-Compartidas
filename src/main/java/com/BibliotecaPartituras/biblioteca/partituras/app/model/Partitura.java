package com.BibliotecaPartituras.biblioteca.partituras.app.model;

import jakarta.persistence.*;

@Entity
public class Partitura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String compositor;

    @Column(nullable = false)
    private String instrumento;

    @Column(nullable = false)
    private String genero;

    private String dificultad;
    private String etiquetas;

    @Column(nullable = false)
    private String archivoPdf;

    @ManyToOne
    private Usuario usuario;

    // Getters y setters
    public Long getId() { return id; }
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
    public String getEtiquetas() { return etiquetas; }
    public void setEtiquetas(String etiquetas) { this.etiquetas = etiquetas; }
    public String getArchivoPdf() { return archivoPdf; }
    public void setArchivoPdf(String archivoPdf) { this.archivoPdf = archivoPdf; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
