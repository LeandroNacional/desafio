package com.mvdesafio.backend.service.impl;

import com.mvdesafio.backend.dto.OpcaoDTO;
import com.mvdesafio.backend.repository.ColaboradorRepository;
import com.mvdesafio.backend.repository.CafeDaManhaRepository;
import com.mvdesafio.backend.repository.OpcaoRepository;
import com.mvdesafio.backend.service.OpcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mvdesafio.backend.exception.BusinessException;
import com.mvdesafio.backend.model.Colaborador;
import com.mvdesafio.backend.model.CafeDaManha;
import com.mvdesafio.backend.model.Opcao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpcaoServiceImpl implements OpcaoService {

    private final OpcaoRepository opcaoRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final CafeDaManhaRepository cafeDaManhaRepository;

    @Autowired
    public OpcaoServiceImpl(
        OpcaoRepository opcaoRepository,
        ColaboradorRepository colaboradorRepository,
        CafeDaManhaRepository cafeDaManhaRepository
    ) {
        this.opcaoRepository = opcaoRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.cafeDaManhaRepository = cafeDaManhaRepository;
    }

    @Override
    public OpcaoDTO cadastrar(OpcaoDTO dto) {
        Colaborador colaborador = colaboradorRepository.findById(dto.getColaboradorId())
            .orElseThrow(() -> new BusinessException("Colaborador não encontrado."));

        CafeDaManha cafe = cafeDaManhaRepository.findById(dto.getCafeDaManhaId())
            .orElseThrow(() -> new BusinessException("Café da manhã não encontrado."));

        if (opcaoRepository.findByNomeAndCafeDaManha(dto.getNome(), cafe.getId()).isPresent()) {
            throw new BusinessException("Já existe essa opção para o café da manhã nesta data.");
        }

        Opcao opcao = new Opcao();
        opcao.setNome(dto.getNome());
        opcao.setStatus(Opcao.Status.NAO_TROUXE);
        opcao.setColaborador(colaborador);
        opcao.setCafeDaManha(cafe);
        opcao = opcaoRepository.save(opcao);

        dto.setId(opcao.getId());
        dto.setStatus(opcao.getStatus().name());
        return dto;
    }

    @Override
    public OpcaoDTO marcarStatus(Long opcaoId, boolean trouxe) {
        Opcao opcao = opcaoRepository.findById(opcaoId)
            .orElseThrow(() -> new BusinessException("Opção não encontrada."));

        opcao.setStatus(trouxe ? Opcao.Status.TROUXE : Opcao.Status.NAO_TROUXE);
        opcao = opcaoRepository.save(opcao);

        OpcaoDTO dto = new OpcaoDTO();
        dto.setId(opcao.getId());
        dto.setNome(opcao.getNome());
        dto.setStatus(opcao.getStatus().name());
        dto.setColaboradorId(opcao.getColaborador().getId());
        dto.setCafeDaManhaId(opcao.getCafeDaManha().getId());
        return dto;
    }

    @Override
    public List<OpcaoDTO> listarPorColaborador(Long colaboradorId) {
        return opcaoRepository.findByColaboradorId(colaboradorId).stream().map(opcao -> {
            OpcaoDTO dto = new OpcaoDTO();
            dto.setId(opcao.getId());
            dto.setNome(opcao.getNome());
            dto.setStatus(opcao.getStatus().name());
            dto.setColaboradorId(opcao.getColaborador().getId());
            dto.setCafeDaManhaId(opcao.getCafeDaManha().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public OpcaoDTO editarOpcao(Long id, OpcaoDTO dto) {
        Opcao opcao = opcaoRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Opção não encontrada."));

        opcao.setNome(dto.getNome());
        opcao = opcaoRepository.save(opcao);

        dto.setId(opcao.getId());
        dto.setStatus(opcao.getStatus().name());
        dto.setColaboradorId(opcao.getColaborador().getId());
        dto.setCafeDaManhaId(opcao.getCafeDaManha().getId());
        return dto;
    }

    @Override
    public List<OpcaoDTO> listarPorCafe(Long cafeId) {
        List<Opcao> opcoes = opcaoRepository.findByCafeDaManhaId(cafeId);
        return opcoes.stream().map(this::toDTO).toList();
    }

    private OpcaoDTO toDTO(Opcao opcao) {
        OpcaoDTO dto = new OpcaoDTO();
        dto.setId(opcao.getId());
        dto.setNome(opcao.getNome());
        dto.setStatus(opcao.getStatus().name());
        dto.setColaboradorId(opcao.getColaborador().getId());
        dto.setCafeDaManhaId(opcao.getCafeDaManha().getId());
        return dto;
    }
}
