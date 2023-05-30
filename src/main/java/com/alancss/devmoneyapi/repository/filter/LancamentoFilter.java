package com.alancss.devmoneyapi.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record LancamentoFilter(
        String descricao,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dataVencimentoDe,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dataVencimentoAte) {
}
