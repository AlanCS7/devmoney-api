package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.repository.CategoriaRepository;
import com.alancss.devmoneyapi.repository.LancamentoRepository;
import com.alancss.devmoneyapi.repository.PessoaRepository;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.service.exceptions.CategoriaInexistenteException;
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
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Lançamento com ID %d não encontrada", id)));
    }

    public Lancamento create(Lancamento lancamento) {
        existsCategoria(lancamento);

        Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getId())
                .orElseThrow(() -> new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d não encontrada", lancamento.getPessoa().getId())));

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
        categoriaRepository.findById(lancamento.getCategoria().getId())
                .orElseThrow(() -> new CategoriaInexistenteException(String.format("Categoria com ID %d não encontrada", lancamento.getCategoria().getId())));
    }
}
