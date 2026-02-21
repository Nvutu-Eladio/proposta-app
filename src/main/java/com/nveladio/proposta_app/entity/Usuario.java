package com.nveladio.proposta_app.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String sobreNome;

    private String cpf;

    private String telefone;

    private String renda;

    @OneToOne(mappedBy = "usuario")
    private Proposta proposta;


}
