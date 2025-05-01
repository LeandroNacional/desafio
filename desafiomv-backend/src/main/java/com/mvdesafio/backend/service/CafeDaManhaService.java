package com.mvdesafio.backend.service;

import com.mvdesafio.backend.dto.CafeDaManhaDTO;
import com.mvdesafio.backend.dto.ParticipacaoDTO;
import com.mvdesafio.backend.dto.ParticipanteDetalheDTO;
import java.util.List;

public interface CafeDaManhaService {
    CafeDaManhaDTO cadastrar(CafeDaManhaDTO dto);
    List<CafeDaManhaDTO> listarTodos();
    CafeDaManhaDTO editar(Long id, CafeDaManhaDTO dto);
    void adicionarParticipante(Long cafeId, ParticipacaoDTO dto);
    List<ParticipanteDetalheDTO> listarParticipantesDetalhado(Long cafeId);
}
