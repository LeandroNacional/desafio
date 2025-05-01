package com.mvdesafio.backend;

import com.mvdesafio.backend.model.CafeDaManha;
import com.mvdesafio.backend.model.Colaborador;
import com.mvdesafio.backend.model.Opcao;
import com.mvdesafio.backend.repository.CafeDaManhaRepository;
import com.mvdesafio.backend.repository.ColaboradorRepository;
import com.mvdesafio.backend.repository.OpcaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OpcaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OpcaoRepository opcaoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private CafeDaManhaRepository cafeDaManhaRepository;


    private Opcao opcao;

    @BeforeEach
    void setUp() {
        opcaoRepository.deleteAll();
        colaboradorRepository.deleteAll();
        cafeDaManhaRepository.deleteAll();

        Colaborador colaborador = new Colaborador();
        colaborador.setNome("Teste");
        colaborador.setCpf("12345678901");
        colaborador = colaboradorRepository.save(colaborador);

        CafeDaManha cafe = new CafeDaManha();
        cafe.setNome("Café Teste");
        cafe.setData(LocalDate.now().minusDays(1));
        cafe = cafeDaManhaRepository.save(cafe);

        opcao = new Opcao();
        opcao.setNome("Suco");
        opcao.setColaborador(colaborador);
        opcao.setCafeDaManha(cafe);
        opcao.setStatus(Opcao.Status.NAO_TROUXE);
        opcao = opcaoRepository.save(opcao);
    }

    @Test
    void testAlterarStatusEmQualquerData() throws Exception {
        mockMvc.perform(put("/api/opcoes/" + opcao.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"trouxe\":true}"))
                .andExpect(status().isOk());
    }
}