package com.bibliotecapartituras.BibliotecaPartituras.repository;

import com.bibliotecapartituras.BibliotecaPartituras.model.Partitura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartituraRepository extends JpaRepository<Partitura, Long> {
    // Métodos personalizados si se requieren
}
