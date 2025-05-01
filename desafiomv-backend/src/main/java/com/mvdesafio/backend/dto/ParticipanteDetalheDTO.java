package com.mvdesafio.backend.dto;

import java.util.List;

public class ParticipanteDetalheDTO {
    private Long id;  // Modificado para Long
    private String nome;
    private String cpf;
    private List<OpcaoResumoDTO> opcoes;

    public ParticipanteDetalheDTO() {}

    // Modificado para receber Long no lugar de String para o ID
    public ParticipanteDetalheDTO(Long id, String nome, String cpf, List<OpcaoResumoDTO> opcoes) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.opcoes = opcoes;
    }

    public Long getId() {  // Modificado para retornar Long
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<OpcaoResumoDTO> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<OpcaoResumoDTO> opcoes) {
        this.opcoes = opcoes;
    }
}
