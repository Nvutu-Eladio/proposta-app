package com.nveladio.proposta_app.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private String observacao;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
