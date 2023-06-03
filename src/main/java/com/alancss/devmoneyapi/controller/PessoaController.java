package com.alancss.devmoneyapi.controller;

import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.alancss.devmoneyapi.utils.AppUtils.getUri;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pessoa> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody @Valid Pessoa pessoa) {
        Pessoa pessoaSalva = service.create(pessoa);
        return ResponseEntity
                .created(getUri(pessoaSalva.getId()))
                .body(pessoaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") Long id, @RequestBody @Valid Pessoa pessoa) {
        return ResponseEntity.ok(service.update(id, pessoa));
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsAtivo(@PathVariable("id") Long id, @RequestBody Boolean ativo) {
        service.updateIsAtivo(id, ativo);
    }
}
