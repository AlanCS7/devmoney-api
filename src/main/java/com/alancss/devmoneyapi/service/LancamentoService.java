package com.alancss.devmoneyapi.service;

import com.alancss.devmoneyapi.model.Categoria;
import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.model.Pessoa;
import com.alancss.devmoneyapi.repository.CategoriaRepository;
import com.alancss.devmoneyapi.repository.LancamentoRepository;
import com.alancss.devmoneyapi.repository.PessoaRepository;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.repository.projection.ResumoLancamento;
import com.alancss.devmoneyapi.service.exception.BusinessException;
import com.alancss.devmoneyapi.service.exception.PessoaInexistenteOuInativaException;
import com.alancss.devmoneyapi.service.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
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

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return repository.resumir(lancamentoFilter, pageable);
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
            throw new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d não encontrada", lancamento.getPessoa().getId()));
        }

        if (pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d está inativa", pessoa.getId()));
        }

        return repository.save(lancamento);
    }


    public Lancamento update(Long id, Lancamento lancamento) {
        Lancamento lancamentoDB = repository.findOne(id);

        if (lancamento == null) {
            throw new ResourceNotFoundException(String.format("Lançamento com ID %d não encontrada", id));
        }

        existsCategoria(lancamento);

        if (!lancamento.getPessoa().equals(lancamentoDB.getPessoa())) {
            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoDB, "id");

        return repository.save(lancamentoDB);
    }


    public void delete(Long id) {
        Lancamento lancamento = getById(id);
        repository.delete(lancamento);
    }

    private void existsCategoria(Lancamento lancamento) {
        Categoria categoria = null;
        if (lancamento.getCategoria().getId() != null) {
            categoria = categoriaRepository.findOne(lancamento.getCategoria().getId());
        }

        if (categoria == null) {
            throw new BusinessException(String.format("Categoria com ID %d não encontrada", lancamento.getCategoria().getId()));
        }
    }

    private void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = null;
        if (lancamento.getPessoa().getId() != null) {
            pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
        }

        if (pessoa == null) {
            throw new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d não encontrada", lancamento.getPessoa().getId()));
        }

        if (pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException(String.format("Pessoa com ID %d está inativa", pessoa.getId()));
        }
    }
}
