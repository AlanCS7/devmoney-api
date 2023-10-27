package com.alancss.devmoneyapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "permissao")
@Data
@EqualsAndHashCode(of = "id")
public class Permissao {

    @Id
    private Long id;
    private String descricao;

}
