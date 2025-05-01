package com.mvdesafio.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvdesafio.backend.model.CafeDaManha;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CafeDaManhaParticipacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CafeDaManhaRepository cafeDaManhaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private OpcaoRepository opcaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CafeDaManha cafe;

    @BeforeEach
    void setUp() {
        opcaoRepository.deleteAll();
        colaboradorRepository.deleteAll();
        cafeDaManhaRepository.deleteAll();

        cafe = new CafeDaManha();
        cafe.setNome("Café Teste");
        cafe.setData(LocalDate.now().plusDays(2));
        cafe = cafeDaManhaRepository.save(cafe);
    }

    @Test
    void testParticipacaoComSucesso() throws Exception {
        Map<String, Object> payload = Map.of(
            "nome", "Fulano de Tal",
            "cpf", "12345678901",
            "opcoes", List.of("Pão", "Suco de Laranja")
        );

        mockMvc.perform(post("/api/cafes/" + cafe.getId() + "/participantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        // Verifica se colaborador e opções foram criados corretamente
        assertThat(colaboradorRepository.findByCpf("12345678901")).isPresent();
        var opcoes = opcaoRepository.findByCafeDaManhaId(cafe.getId());
        assertThat(opcoes).hasSize(2);
        assertThat(opcoes).anyMatch(o -> o.getNome().equals("Pão"));
        assertThat(opcoes).anyMatch(o -> o.getNome().equals("Suco de Laranja"));
    }

    @Test
    void testParticipacaoComCpfInvalido() throws Exception {
        Map<String, Object> payload = Map.of(
            "nome", "Fulano de Tal",
            "cpf", "123", // CPF inválido
            "opcoes", List.of("Pão")
        );

        mockMvc.perform(post("/api/cafes/" + cafe.getId() + "/participantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
