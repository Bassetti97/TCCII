package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.fundatec.org.controller.request.AgendamentoRequest;
import tcc.fundatec.org.controller.response.AgendamentoResponse;
import tcc.fundatec.org.model.Agendamento;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.AgendamentoRepository;
import tcc.fundatec.org.repository.ClienteRepository;
import tcc.fundatec.org.repository.EstabelecimentoRepository;
import tcc.fundatec.org.service.AgendamentoService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    private Cliente cliente;
    private Agendamento agendamento;
    private AgendamentoRequest agendamentoRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = Cliente.builder()
                .id(1L)
                .nome("Isabela Gabriela Marques")  // Nome precisa bater
                .build();

        Estabelecimento estabelecimento = Estabelecimento.builder()
                .id(1L)
                .nome("isabel e cia")  // Nome precisa bater
                .endereco("Endereco")
                .contato("Contato")
                .build();

        agendamento = Agendamento.builder()
                .id(1L)
                .dataHorario(LocalDateTime.now())
                .tipoServico("Corte de Cabelo")
                .cliente(cliente)  // Usar a instância criada
                .estabelecimento(estabelecimento)
                .build();

        agendamentoRequest = new AgendamentoRequest(LocalDateTime.now(), "Corte de Cabelo", "Isabela Gabriela Marques", "isabel e cia");
    }

    @Test
    void testFindAll() {
        when(agendamentoRepository.findAll()).thenReturn(Collections.singletonList(agendamento));

        var agendamentos = agendamentoService.findAll();

        assertNotNull(agendamentos);
        assertEquals(1, agendamentos.size());
        assertEquals(agendamento.getId(), agendamentos.get(0).getId());
        verify(agendamentoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));

        Optional<AgendamentoResponse> response = agendamentoService.findById(1L);

        assertTrue(response.isPresent());
        assertEquals(agendamento.getId(), response.get().getId());
        verify(agendamentoRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(clienteRepository.findByNomeContainingIgnoreCase("Isabela Gabriela Marques")).thenReturn(Optional.of(agendamento.getCliente()));
        when(estabelecimentoRepository.findByNomeContainingIgnoreCase("isabel e cia")).thenReturn(Optional.of(agendamento.getEstabelecimento()));
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        AgendamentoResponse response = agendamentoService.save(agendamentoRequest);

        assertNotNull(response);
        assertEquals(agendamento.getId(), response.getId());
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }


    @Test
    void testUpdate() {
        // Mock para encontrar o Agendamento
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));

        // Mock para encontrar o Cliente pelo nome
        when(clienteRepository.findByNomeContainingIgnoreCase("Isabela Gabriela Marques")).thenReturn(Optional.of(agendamento.getCliente()));

        // Mock para encontrar o Estabelecimento pelo nome
        when(estabelecimentoRepository.findByNomeContainingIgnoreCase("isabel e cia")).thenReturn(Optional.of(agendamento.getEstabelecimento()));

        // Mock para salvar o Agendamento
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Chamada do método de atualização
        AgendamentoResponse response = agendamentoService.update(1L, agendamentoRequest);

        // Verificações
        assertNotNull(response);
        assertEquals(agendamento.getId(), response.getId());
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }


    @Test
    void testDeleteById() {
        agendamentoService.deleteById(1L);
        verify(agendamentoRepository, times(1)).deleteById(1L);
    }
}
