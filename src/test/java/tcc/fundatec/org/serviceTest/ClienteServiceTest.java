package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.fundatec.org.controller.request.ClienteRequest;
import tcc.fundatec.org.controller.response.ClienteResponse;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.repository.ClienteRepository;
import tcc.fundatec.org.service.ClienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private ClienteRequest clienteRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = Cliente.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .logradouro("Rua A")
                .complemento("Apto 1")
                .cep("12345-678")
                .telefone("99999-9999")
                .email("joao.silva@example.com")
                .build();

        clienteRequest = ClienteRequest.builder()
                .nome("João Silva")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .logradouro("Rua A")
                .complemento("Apto 1")
                .cep("12345-678")
                .telefone("99999-9999")
                .email("joao.silva@example.com")
                .build();
    }

    // Teste positivo para encontrar todos os clientes
    @Test
    void testFindAllClients() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteResponse> clientes = clienteService.findAll();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals(cliente.getNome(), clientes.get(0).getNome());

        verify(clienteRepository, times(1)).findAll();
    }

    // Teste negativo para quando não há clientes
    @Test
    void testFindAllClientsEmpty() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        List<ClienteResponse> clientes = clienteService.findAll();
        assertTrue(clientes.isEmpty());

        verify(clienteRepository, times(1)).findAll();
    }

    // Teste positivo para encontrar um cliente pelo ID
    @Test
    void testFindClientById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<ClienteResponse> clienteResponse = clienteService.findById(1L);
        assertTrue(clienteResponse.isPresent());
        assertEquals(cliente.getNome(), clienteResponse.get().getNome());

        verify(clienteRepository, times(1)).findById(1L);
    }

    // Teste negativo para quando o cliente não é encontrado pelo ID
    @Test
    void testFindClientByIdNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ClienteResponse> clienteResponse = clienteService.findById(1L);
        assertFalse(clienteResponse.isPresent());

        verify(clienteRepository, times(1)).findById(1L);
    }

    // Teste positivo para salvar um novo cliente
    @Test
    void testSaveClient() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponse savedClient = clienteService.save(clienteRequest);
        assertNotNull(savedClient);
        assertEquals(cliente.getNome(), savedClient.getNome());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    // Teste positivo para atualizar um cliente existente
    @Test
    void testUpdateClient() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        ClienteResponse updatedClient = clienteService.update(1L, clienteRequest);
        assertNotNull(updatedClient);
        assertEquals(cliente.getNome(), updatedClient.getNome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    // Teste negativo para tentar atualizar um cliente inexistente
    @Test
    void testUpdateClientNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.update(1L, clienteRequest);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    // Teste positivo para deletar um cliente
    @Test
    void testDeleteClient() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deleteById(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    // Teste positivo para buscar clientes pelo nome
    @Test
    void testFindClientByName() {
        when(clienteRepository.findByNomeContainingIgnoreCase("João")).thenReturn(Optional.of(cliente));

        List<ClienteResponse> clientes = clienteService.findByNome("João");
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals(cliente.getNome(), clientes.get(0).getNome());

        verify(clienteRepository, times(1)).findByNomeContainingIgnoreCase("João");
    }

    // Teste negativo para buscar clientes pelo nome inexistente
    @Test
    void testFindClientByNameNotFound() {
        // Configurando o mock para retornar vazio
        when(clienteRepository.findByNomeContainingIgnoreCase("José")).thenReturn(Optional.empty());

        // Chamando o método que deve retornar uma lista vazia
        List<ClienteResponse> clientes = clienteService.findByNome("José");

        // Verificando se a lista está vazia
        assertTrue(clientes.isEmpty());

        // Verificando se o método do repositório foi chamado corretamente
        verify(clienteRepository, times(1)).findByNomeContainingIgnoreCase("José");
    }
}
