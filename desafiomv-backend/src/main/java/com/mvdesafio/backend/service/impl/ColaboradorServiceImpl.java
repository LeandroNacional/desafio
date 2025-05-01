package com.mvdesafio.backend.service.impl;

import com.mvdesafio.backend.dto.ColaboradorDTO;
import com.mvdesafio.backend.exception.BusinessException;
import com.mvdesafio.backend.model.Colaborador;
import com.mvdesafio.backend.repository.ColaboradorRepository;
import com.mvdesafio.backend.service.ColaboradorService;
import com.mvdesafio.backend.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Override
    public ColaboradorDTO cadastrar(ColaboradorDTO dto) {
        if (!CpfUtil.isValid(dto.getCpf())) {
            throw new BusinessException("CPF deve conter 11 dígitos numéricos.");
        }
        if (colaboradorRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new BusinessException("Já existe colaborador com este CPF.");
        }
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNome(dto.getNome());
        colaborador.setCpf(dto.getCpf());
        colaborador = colaboradorRepository.save(colaborador);

        dto.setId(colaborador.getId());
        return dto;
    }

    @Override
    public ColaboradorDTO buscarPorCpf(String cpf) {
        Colaborador colaborador = colaboradorRepository.findByCpf(cpf)
            .orElseThrow(() -> new BusinessException("Colaborador não encontrado."));
        ColaboradorDTO dto = new ColaboradorDTO();
        dto.setId(colaborador.getId());
        dto.setNome(colaborador.getNome());
        dto.setCpf(colaborador.getCpf());
        return dto;
    }

    @Override
    public void deletar(Long id) {
        colaboradorRepository.deleteById(id);
    }
}
