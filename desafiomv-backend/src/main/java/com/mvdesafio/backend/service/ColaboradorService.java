package com.mvdesafio.backend.service;

import com.mvdesafio.backend.dto.ColaboradorDTO;

public interface ColaboradorService {
    ColaboradorDTO cadastrar(ColaboradorDTO dto);
    ColaboradorDTO buscarPorCpf(String cpf);
    void deletar(Long id);
}
