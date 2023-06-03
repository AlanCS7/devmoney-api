package com.alancss.devmoneyapi.controller;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alancss.devmoneyapi.utils.AppUtils.getUri;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Lancamento> getByParams(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return service.getByParams(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Lancamento> create(@RequestBody @Valid Lancamento categoria) {
        Lancamento lancamentoSalva = service.create(categoria);
        return ResponseEntity
                .created(getUri(lancamentoSalva.getId()))
                .body(lancamentoSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
