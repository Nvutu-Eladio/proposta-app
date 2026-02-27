package com.nveladio.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropostaRequestDto {

    private String nome;

    private String sobreNome;

    private String telefone;

    private String cpf;

    private Double renda;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean integrada;

}
