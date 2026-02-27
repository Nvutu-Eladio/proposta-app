package com.nveladio.proposta_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    private Boolean integrada;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    private Usuario usuario;

}
