package com.mvdesafio.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

public class CafeDaManhaDTO {
    private Long id;

    @NotBlank(message = "Nome do café da manhã é obrigatório")
    private String nome;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

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
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
}
