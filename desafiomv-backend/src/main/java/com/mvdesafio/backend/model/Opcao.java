package com.mvdesafio.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opcao", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome", "cafe_da_manha_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "cafe_da_manha_id", nullable = false)
    private CafeDaManha cafeDaManha;

    public enum Status {
        TROUXE, NAO_TROUXE
    }
    
    
}
