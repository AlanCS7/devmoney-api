package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.repository.LancamentoRepository;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.service.exceptions.PessoaInativaException;
import com.alancss.devmoneyapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private PessoaService pessoaService;

    public Page<Lancamento> getByParams(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return repository.findByParams(lancamentoFilter, pageable);
    }

    public Lancamento getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Lançamento com ID %d não encontrada", id)));
    }

    public Lancamento create(Lancamento lancamento) {
        Pessoa pessoa = pessoaService.getById(lancamento.getPessoa().getId());

        if (pessoa.isInativo()) {
            throw new PessoaInativaException(String.format("Pessoa com ID %d está inativa", pessoa.getId()));
        }

        return repository.save(lancamento);
    }

    public void delete(Long id) {
        Lancamento lancamento = getById(id);
        repository.delete(lancamento);
    }
}
