package com.alancss.devmoneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.alancss.devmoneyapi.model.enums.TipoLancamento;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "lancamento")
@Data
@EqualsAndHashCode(of = "id")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo descricao deve ser obrigatório")
    private String descricao;

    @NotNull(message = "O campo data de vencimento deve ser obrigatório")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull(message = "O campo valor deve ser obrigatório")
    private BigDecimal valor;

    private String observacao;

    @NotNull(message = "O campo tipo deve ser obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @NotNull(message = "O campo categoria deve ser obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotNull(message = "O campo pessoa deve ser obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

}
