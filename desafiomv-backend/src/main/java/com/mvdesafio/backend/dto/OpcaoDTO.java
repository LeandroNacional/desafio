package com.mvdesafio.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class OpcaoDTO {
    private Long id;

    @NotBlank(message = "Nome da opção é obrigatório")
    private String nome;

    private String status;
    private Long colaboradorId;
    private Long cafeDaManhaId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getColaboradorId() {
        return colaboradorId;
    }
    public void setColaboradorId(Long colaboradorId) {
        this.colaboradorId = colaboradorId;
    }
    public Long getCafeDaManhaId() {
        return cafeDaManhaId;
    }
    public void setCafeDaManhaId(Long cafeDaManhaId) {
        this.cafeDaManhaId = cafeDaManhaId;
    }
}
