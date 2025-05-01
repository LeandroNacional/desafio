package com.mvdesafio.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cafe_da_manha", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"data"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CafeDaManha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate data;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
