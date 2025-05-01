package com.mvdesafio.backend.service;

import com.mvdesafio.backend.dto.OpcaoDTO;
import java.util.List;

public interface OpcaoService {
    OpcaoDTO cadastrar(OpcaoDTO dto);
    OpcaoDTO marcarStatus(Long opcaoId, boolean trouxe);
    List<OpcaoDTO> listarPorColaborador(Long colaboradorId);
    OpcaoDTO editarOpcao(Long id, OpcaoDTO dto);
    List<OpcaoDTO> listarPorCafe(Long cafeId);
}
