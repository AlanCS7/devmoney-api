package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Categoria;
import com.alancss.devmoneyapi.repository.CategoriaRepository;
import com.alancss.devmoneyapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll() {
        return repository.findAll();
    }

    public Categoria getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Categoria com ID %d não encontrada", id)));
    }

    public Categoria create(Categoria categoria) {
        return repository.save(categoria);
    }
}
