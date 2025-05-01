package com.mvdesafio.backend;

import com.mvdesafio.backend.dto.OpcaoDTO;
import com.mvdesafio.backend.exception.BusinessException;
import com.mvdesafio.backend.model.CafeDaManha;
import com.mvdesafio.backend.model.Colaborador;
import com.mvdesafio.backend.model.Opcao;
import com.mvdesafio.backend.repository.CafeDaManhaRepository;
import com.mvdesafio.backend.repository.ColaboradorRepository;
import com.mvdesafio.backend.repository.OpcaoRepository;
import com.mvdesafio.backend.service.impl.OpcaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
public class OpcaoServiceImplTest {

    private OpcaoRepository opcaoRepository;
    private ColaboradorRepository colaboradorRepository;
    private CafeDaManhaRepository cafeDaManhaRepository;
    private OpcaoServiceImpl opcaoService;

    @BeforeEach
    void setUp() {
        opcaoRepository = mock(OpcaoRepository.class);
        colaboradorRepository = mock(ColaboradorRepository.class);
        cafeDaManhaRepository = mock(CafeDaManhaRepository.class);
        opcaoService = new OpcaoServiceImpl(opcaoRepository, colaboradorRepository, cafeDaManhaRepository);
    }

    @Test
    void testMarcarStatus_TrocarStatusParaTrouxe() {
        Opcao opcao = new Opcao();
        opcao.setId(1L);
        opcao.setNome("Pão");
        Colaborador colaborador = new Colaborador();
        colaborador.setId(1L);
        opcao.setColaborador(colaborador);
        CafeDaManha cafe = new CafeDaManha();
        cafe.setId(1L);
        opcao.setCafeDaManha(cafe);

        when(opcaoRepository.findById(1L)).thenReturn(Optional.of(opcao));
        when(opcaoRepository.save(any(Opcao.class))).thenAnswer(i -> i.getArgument(0));

        OpcaoDTO dto = opcaoService.marcarStatus(1L, true);

        assertEquals("TROUXE", dto.getStatus());
        verify(opcaoRepository).save(any(Opcao.class));
    }

    @Test
    void testMarcarStatus_TrocarStatusParaNaoTrouxe() {
        Opcao opcao = new Opcao();
        opcao.setId(2L);
        opcao.setNome("Bolo");
        Colaborador colaborador = new Colaborador();
        colaborador.setId(2L);
        opcao.setColaborador(colaborador);
        CafeDaManha cafe = new CafeDaManha();
        cafe.setId(2L);
        opcao.setCafeDaManha(cafe);

        when(opcaoRepository.findById(2L)).thenReturn(Optional.of(opcao));
        when(opcaoRepository.save(any(Opcao.class))).thenAnswer(i -> i.getArgument(0));

        OpcaoDTO dto = opcaoService.marcarStatus(2L, false);

        assertEquals("NAO_TROUXE", dto.getStatus());
        verify(opcaoRepository).save(any(Opcao.class));
    }

    @Test
    void testMarcarStatus_OpcoesNaoEncontrada() {
        when(opcaoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> opcaoService.marcarStatus(99L, true));
    }
}