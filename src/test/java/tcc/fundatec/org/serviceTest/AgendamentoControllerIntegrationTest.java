package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class AgendamentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAgendamento() throws Exception {
        String jsonContent = "{ \"dataHorario\": \"2024-09-10T14:00:00\", \"cliente\": { \"id\": 1 }, \"estabelecimento\": { \"id\": 1 } }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/agendamentos")
                        .contentType("application/json")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
