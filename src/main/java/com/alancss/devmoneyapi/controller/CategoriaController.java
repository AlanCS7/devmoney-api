package com.alancss.devmoneyapi.controller;

import com.alancss.devmoneyapi.model.Categoria;
import com.alancss.devmoneyapi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alancss.devmoneyapi.utils.AppUtils.getUri;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody @Valid Categoria categoria) {
        Categoria categoriaSalva = service.create(categoria);
        return ResponseEntity
                .created(getUri(categoriaSalva.getId()))
                .body(categoriaSalva);
    }

}
