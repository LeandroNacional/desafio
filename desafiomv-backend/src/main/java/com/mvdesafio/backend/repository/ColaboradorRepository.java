package com.mvdesafio.backend.repository;

import com.mvdesafio.backend.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    @Query(value = "SELECT * FROM colaborador WHERE cpf = :cpf", nativeQuery = true)
    Optional<Colaborador> findByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM colaborador WHERE nome = :nome", nativeQuery = true)
    Optional<Colaborador> findByNome(@Param("nome") String nome);
}
