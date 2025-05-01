package com.mvdesafio.backend.controller;

import com.mvdesafio.backend.dto.OpcaoDTO;
import com.mvdesafio.backend.service.OpcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/opcoes")
public class OpcaoController {

    @Autowired
    private OpcaoService opcaoService;

    @PostMapping
    public ResponseEntity<OpcaoDTO> cadastrar(@RequestBody @Valid OpcaoDTO dto) {
        return ResponseEntity.ok(opcaoService.cadastrar(dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OpcaoDTO> marcarStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        boolean trouxe = body.getOrDefault("trouxe", false);
        return ResponseEntity.ok(opcaoService.marcarStatus(id, trouxe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpcaoDTO> editarOpcao(@PathVariable Long id, @RequestBody OpcaoDTO dto) {
        return ResponseEntity.ok(opcaoService.editarOpcao(id, dto));
    }

    @GetMapping("/cafes/{cafeId}/opcoes")
    public List<OpcaoDTO> listarOpcoesPorCafe(@PathVariable Long cafeId) {
        return opcaoService.listarPorCafe(cafeId);
    }
    
    @GetMapping("/colaborador/{colaboradorId}/opcoes")
    public List<OpcaoDTO> listarOpcoesPorColaborador(@PathVariable Long colaboradorId){
        return opcaoService.listarPorColaborador(colaboradorId);
    }
}
