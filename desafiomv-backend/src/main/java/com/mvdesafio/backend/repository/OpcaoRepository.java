package com.mvdesafio.backend.repository;

import com.mvdesafio.backend.model.Opcao;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpcaoRepository extends JpaRepository<Opcao, Long> {
    @Query(value = "SELECT * FROM opcao WHERE nome = :nome AND cafe_da_manha_id = :cafeId", nativeQuery = true)
    Optional<Opcao> findByNomeAndCafeDaManha(@Param("nome") String nome, @Param("cafeId") Long cafeId);

    @Query(value = "SELECT * FROM opcao WHERE colaborador_id = :colaboradorId", nativeQuery = true)
    List<Opcao> findByColaboradorId(@Param("colaboradorId") Long colaboradorId);

    @Query(value = "SELECT * FROM opcao WHERE cafe_da_manha_id = :cafeId", nativeQuery = true)
    List<Opcao> findByCafeDaManhaId(@Param("cafeId") Long cafeId);
}
