package com.alancss.devmoneyapi.repository.lancamento;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> findByParams(LancamentoFilter lancamentoFilter, Pageable pageable);
}
