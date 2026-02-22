package com.nveladio.proposta_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
