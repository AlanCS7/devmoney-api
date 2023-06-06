package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.repository.PessoaRepository;
import com.alancss.devmoneyapi.service.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> getAll() {
        return repository.findAll();
    }

    public Pessoa getById(Long id) {

        Pessoa pessoa = repository.findOne(id);

        if (pessoa == null) {
            throw new ResourceNotFoundException(String.format("Pessoa com ID %d não encontrada", id));
        }
        return pessoa;
    }

    public Pessoa create(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    public void delete(Long id) {
        Pessoa pessoa = getById(id);
        repository.delete(pessoa);
    }

    public Pessoa update(Long id, Pessoa pessoa) {
        Pessoa pessoaDB = getById(id);
        BeanUtils.copyProperties(pessoa, pessoaDB, "id");
        return repository.save(pessoaDB);
    }

    public void updateIsAtivo(Long id, Boolean ativo) {
        Pessoa pessoaDB = getById(id);
        pessoaDB.setAtivo(ativo);
        repository.save(pessoaDB);
    }
}
