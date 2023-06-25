package com.alancss.devmoneyapi.repository;

import com.alancss.devmoneyapi.model.Lancamento;
import com.alancss.devmoneyapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
