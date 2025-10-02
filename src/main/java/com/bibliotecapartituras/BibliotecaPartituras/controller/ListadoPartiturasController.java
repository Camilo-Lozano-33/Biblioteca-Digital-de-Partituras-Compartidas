
package com.bibliotecapartituras.BibliotecaPartituras.controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotecapartituras.BibliotecaPartituras.model.Partitura;
import com.bibliotecapartituras.BibliotecaPartituras.repository.PartituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListadoPartiturasController {
    @RequestMapping("/")
    public String inicio() {
        return "index";
    }
    @Autowired
    private PartituraRepository partituraRepository;

    @GetMapping("/partituras")
    public String listarPartituras(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Partitura> pagina = partituraRepository.findAll(pageable);
        model.addAttribute("pagina", pagina);
        return "listado_partituras";
    }
}
