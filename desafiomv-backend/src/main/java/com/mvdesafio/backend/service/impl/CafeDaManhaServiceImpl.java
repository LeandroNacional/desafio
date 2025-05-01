package com.mvdesafio.backend.service.impl;

import com.mvdesafio.backend.dto.CafeDaManhaDTO;
import com.mvdesafio.backend.dto.ParticipacaoDTO;
import com.mvdesafio.backend.dto.ParticipanteDetalheDTO;
import com.mvdesafio.backend.dto.OpcaoResumoDTO;
import com.mvdesafio.backend.exception.BusinessException;
import com.mvdesafio.backend.model.CafeDaManha;
import com.mvdesafio.backend.model.Colaborador;
import com.mvdesafio.backend.model.Opcao;
import com.mvdesafio.backend.repository.CafeDaManhaRepository;
import com.mvdesafio.backend.repository.ColaboradorRepository;
import com.mvdesafio.backend.repository.OpcaoRepository;
import com.mvdesafio.backend.service.CafeDaManhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.ArrayList;

@Service
public class CafeDaManhaServiceImpl implements CafeDaManhaService {

    @Autowired
    private CafeDaManhaRepository cafeDaManhaRepository;
    @Autowired
    private ColaboradorRepository colaboradorRepository;
    @Autowired
    private OpcaoRepository opcaoRepository;

    @Override
    public CafeDaManhaDTO cadastrar(CafeDaManhaDTO dto) {
        // Verifica se data é maior que hoje
        if (dto.getData() == null || !dto.getData().isAfter(LocalDate.now())) {
            throw new BusinessException("A data do café deve ser maior que a data atual.");
        }

        // Verifica se já existe café da manhã nesta data
        if (cafeDaManhaRepository.findByData(dto.getData()).isPresent()) {
            throw new BusinessException("Já existe um café da manhã nesta data.");
        }

        CafeDaManha cafe = new CafeDaManha();
        cafe.setNome(dto.getNome());
        cafe.setData(dto.getData());
        cafe = cafeDaManhaRepository.save(cafe);

        dto.setId(cafe.getId());
        return dto;
    }

    @Override
    public List<CafeDaManhaDTO> listarTodos() {
        return cafeDaManhaRepository.findAll().stream().map(cafe -> {
            CafeDaManhaDTO dto = new CafeDaManhaDTO();
            dto.setId(cafe.getId());
            dto.setNome(cafe.getNome());
            dto.setData(cafe.getData());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CafeDaManhaDTO editar(Long id, CafeDaManhaDTO dto) {
        CafeDaManha cafe = cafeDaManhaRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Café da manhã não encontrado."));

        cafe.setNome(dto.getNome());
        cafe.setData(dto.getData());
        cafe = cafeDaManhaRepository.save(cafe);

        dto.setId(cafe.getId());
        return dto;
    }

    @Override
    @Transactional
    public void adicionarParticipante(Long cafeId, ParticipacaoDTO dto) {
        // Validação de CPF
        if (dto.getCpf() == null || !dto.getCpf().matches("\\d{11}")) {
            throw new BusinessException("CPF deve ter 11 dígitos numéricos.");
        }

        CafeDaManha cafe = cafeDaManhaRepository.findById(cafeId)
            .orElseThrow(() -> new BusinessException("Café da manhã não encontrado."));

        // Buscar ou criar colaborador
        Colaborador colaborador = colaboradorRepository.findByCpf(dto.getCpf())
            .orElseGet(() -> {
                Colaborador novo = new Colaborador();
                novo.setNome(dto.getNome());
                novo.setCpf(dto.getCpf());
                return colaboradorRepository.save(novo);
            });

        boolean jaParticipa = opcaoRepository.findByColaboradorId(colaborador.getId())
            .stream()
            .anyMatch(opcao -> opcao.getCafeDaManha().getId().equals(cafeId));
        if (jaParticipa) {
            throw new BusinessException("Colaborador já está participando deste café da manhã.");
        }

        for (String nomeOpcao : dto.getOpcoes()) {
            boolean opcaoJaExiste = opcaoRepository.findByNomeAndCafeDaManha(nomeOpcao, cafeId).isPresent();
            if (opcaoJaExiste) {
                throw new BusinessException("A opção '" + nomeOpcao + "' já foi cadastrada para este café da manhã.");
            }
        }

        // Cadastrar opções
        for (String nomeOpcao : dto.getOpcoes()) {
            Opcao opcao = new Opcao();
            opcao.setNome(nomeOpcao);
            opcao.setColaborador(colaborador);
            opcao.setCafeDaManha(cafe);
            opcao.setStatus(Opcao.Status.NAO_TROUXE);
            opcaoRepository.save(opcao);
        }
    }

    @Override
    public List<ParticipanteDetalheDTO> listarParticipantesDetalhado(Long cafeId) {
        // Busca todas as opções desse café
        List<Opcao> opcoes = opcaoRepository.findByCafeDaManhaId(cafeId);

        // Agrupa por colaborador
        Map<Colaborador, List<Opcao>> agrupado = opcoes.stream()
            .collect(Collectors.groupingBy(Opcao::getColaborador));

        // Monta DTO de resposta
        List<ParticipanteDetalheDTO> resultado = new ArrayList<>();
        for (Map.Entry<Colaborador, List<Opcao>> entry : agrupado.entrySet()) {
            Colaborador colaborador = entry.getKey();
            List<OpcaoResumoDTO> opcoesDTO = entry.getValue().stream()
                .map(opcao -> new OpcaoResumoDTO(opcao.getNome(), opcao.getStatus().name()))
                .collect(Collectors.toList());
            resultado.add(new ParticipanteDetalheDTO(colaborador.getId() ,colaborador.getNome(), colaborador.getCpf(), opcoesDTO));
        }
        return resultado;
    }
}
