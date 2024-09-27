package tcc.fundatec.org.serviceTest;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tcc.fundatec.org.controller.AgendamentoController;
import tcc.fundatec.org.service.AgendamentoService;

@SpringBootTest
@ActiveProfiles("test")
public class AgendamentoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateAgendamento() throws Exception {
        String jsonContent = "{ \"dataHorario\": \"2024-09-11T05:00:00\", \"tipoServico\": \"corte\", \"clienteId\": 1, \"estabelecimentoId\": 1 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}