package com.bibliotecapartituras.BibliotecaPartituras.service;

import com.bibliotecapartituras.BibliotecaPartituras.model.Partitura;
import com.bibliotecapartituras.BibliotecaPartituras.model.Usuario;
import com.bibliotecapartituras.BibliotecaPartituras.repository.PartituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PartituraService {
    private static final String UPLOAD_DIR = "uploads/partituras";

    @Autowired
    private PartituraRepository partituraRepository;

    public Partitura guardarPartitura(String titulo, String compositor, String instrumento, String genero, String dificultad, MultipartFile archivoPdf, Usuario usuario) throws IOException {
        // Crear directorio si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Guardar archivo
        String nombreArchivo = System.currentTimeMillis() + "_" + archivoPdf.getOriginalFilename();
        Path filePath = uploadPath.resolve(nombreArchivo);
        archivoPdf.transferTo(filePath.toFile());

        // Guardar entidad
        Partitura partitura = new Partitura();
        partitura.setTitulo(titulo);
        partitura.setCompositor(compositor);
        partitura.setInstrumento(instrumento);
        partitura.setGenero(genero);
        partitura.setDificultad(dificultad);
        partitura.setArchivoPdf(nombreArchivo);
        partitura.setUsuario(usuario);
        return partituraRepository.save(partitura);
    }
}
