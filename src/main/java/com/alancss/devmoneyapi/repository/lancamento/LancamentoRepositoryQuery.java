package com.alancss.devmoneyapi.repository.lancamento;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.repository.filter.LancamentoFilter;
import com.alancss.devmoneyapi.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> findByParams(LancamentoFilter lancamentoFilter, Pageable pageable);

    Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
