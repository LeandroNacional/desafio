package com.mvdesafio.backend.controller;

import com.mvdesafio.backend.dto.ColaboradorDTO;
import com.mvdesafio.backend.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping
    public ResponseEntity<ColaboradorDTO> cadastrar(@RequestBody @Valid ColaboradorDTO dto) {
        return ResponseEntity.ok(colaboradorService.cadastrar(dto));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ColaboradorDTO> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(colaboradorService.buscarPorCpf(cpf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        colaboradorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
