package com.BibliotecaPartituras.biblioteca.partituras.app.repository;

import com.BibliotecaPartituras.biblioteca.partituras.app.model.Partitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartituraRepository extends JpaRepository<Partitura, Long> {
    boolean existsByTituloAndCompositorAndInstrumento(String titulo, String compositor, String instrumento);
    Page<Partitura> findAll(Pageable pageable);
}
