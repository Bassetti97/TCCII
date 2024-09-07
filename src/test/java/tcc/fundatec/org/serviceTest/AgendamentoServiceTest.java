package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tcc.fundatec.org.model.Agendamento;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.AgendamentoRepository;
import tcc.fundatec.org.repository.ClienteRepository;
import tcc.fundatec.org.service.AgendamentoService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
public class AgendamentoServiceTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    public void testClienteAndAgendamentoRelationship() {
        // Cria e salva um Cliente
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("12345678900");
        // Configure outros atributos...
        cliente = clienteRepository.save(cliente);

        // Cria e salva um Agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHorario(LocalDateTime.now());
        agendamento.setCliente(cliente);
        // Configure outros atributos...
        agendamento = agendamentoRepository.save(agendamento);

        // Verifica se o cliente tem o agendamento
        Cliente clienteFromDb = clienteRepository.findById(cliente.getId()).orElse(null);
        assertNotNull(clienteFromDb, "Cliente não encontrado");
        assertEquals(1, clienteFromDb.getAgendamentos().size(), "O número de agendamentos do cliente está incorreto");

        // Verifica se o agendamento está associado ao cliente correto
        Agendamento agendamentoFromDb = agendamentoRepository.findById(agendamento.getId()).orElse(null);
        assertNotNull(agendamentoFromDb, "Agendamento não encontrado");
        assertEquals(cliente.getId(), agendamentoFromDb.getCliente().getId(), "O cliente associado ao agendamento está incorreto");
    }
}
