package com.mvdesafio.backend.dto;

import java.util.List;

public class ParticipacaoDTO {
    private String nome;
    private String cpf;
    private List<String> opcoes;

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
    public List<String> getOpcoes() {
        return opcoes;
    }
    public void setOpcoes(List<String> opcoes) {
        this.opcoes = opcoes;
    }
}
