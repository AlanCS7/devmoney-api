package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Categoria;
import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.repository.CategoriaRepository;
import com.alancss.devmoneyapi.repository.LancamentoRepository;
import com.alancss.devmoneyapi.repository.PessoaRepository;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.service.exceptions.PessoaInexistenteOuInativaException;
import com.alancss.devmoneyapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Page<Lancamento> getByParams(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return repository.findByParams(lancamentoFilter, pageable);
    }

    public Lancamento getById(Long id) {
        Lancamento lancamento = repository.findOne(id);

        if (lancamento == null) {
            throw new ResourceNotFoundException(String.format("Lançamento com ID %d não encontrada", id));
        }

        return lancamento;
    }

    public Lancamento create(Lancamento lancamento) {
        existsCategoria(lancamento);
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());

        if (pessoa == null) {
            throw new ResourceNotFoundException(String.format("Pessoa com ID %d não encontrada", lancamento.getPessoa().getId()));
        }

        if (pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d está inativa", pessoa.getId()));
        }

        return repository.save(lancamento);
    }

    public void delete(Long id) {
        Lancamento lancamento = getById(id);
        repository.delete(lancamento);
    }

    private void existsCategoria(Lancamento lancamento) {
        Categoria categoria = categoriaRepository.findOne(lancamento.getCategoria().getId());

        if (categoria == null) {
            throw new ResourceNotFoundException(String.format("Categoria com ID %d não encontrada", lancamento.getCategoria().getId()));
        }

    }
}
