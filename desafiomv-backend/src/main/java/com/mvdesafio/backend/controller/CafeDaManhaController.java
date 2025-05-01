package com.mvdesafio.backend.controller;

import com.mvdesafio.backend.dto.CafeDaManhaDTO;
import com.mvdesafio.backend.service.CafeDaManhaService;
import com.mvdesafio.backend.dto.ParticipacaoDTO;
import com.mvdesafio.backend.dto.ParticipanteDetalheDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cafes")
public class CafeDaManhaController {

    @Autowired
    private CafeDaManhaService cafeDaManhaService;

    @PostMapping
    public ResponseEntity<CafeDaManhaDTO> cadastrar(@RequestBody @Valid CafeDaManhaDTO dto) {
        return ResponseEntity.ok(cafeDaManhaService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<CafeDaManhaDTO>> listarTodos() {
        return ResponseEntity.ok(cafeDaManhaService.listarTodos());
    }

    @PostMapping("/{cafeId}/participantes")
    public ResponseEntity<?> adicionarParticipante(
        @PathVariable Long cafeId,
        @RequestBody ParticipacaoDTO dto
    ) {
        cafeDaManhaService.adicionarParticipante(cafeId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cafeId}/participantes")
    public ResponseEntity<List<ParticipanteDetalheDTO>> listarParticipantesDetalhado(@PathVariable Long cafeId) {
        return ResponseEntity.ok(cafeDaManhaService.listarParticipantesDetalhado(cafeId));
    }
    
}
