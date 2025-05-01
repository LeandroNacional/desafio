package com.mvdesafio.backend.repository;

import com.mvdesafio.backend.model.CafeDaManha;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CafeDaManhaRepository extends JpaRepository<CafeDaManha, Long> {
    @Query(value = "SELECT * FROM cafe_da_manha WHERE data = :data", nativeQuery = true)
    Optional<CafeDaManha> findByData(@Param("data") LocalDate data);
}
