package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tcc.fundatec.org.controller.request.EstabelecimentoRequest;
import tcc.fundatec.org.controller.response.EstabelecimentoResponse;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.EstabelecimentoRepository;
import tcc.fundatec.org.service.EstabelecimentoService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EstabelecimentoServiceTest {

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEstabelecimento() {
        // Criação do objeto EstabelecimentoRequest
        EstabelecimentoRequest estabelecimentoRequest = new EstabelecimentoRequest(null, "Estabelecimento 1", "Endereco 1", "Contato 1");

        Estabelecimento estabelecimento = Estabelecimento.builder()
                .id(1L)
                .nome(estabelecimentoRequest.getNome())
                .endereco(estabelecimentoRequest.getEndereco())
                .contato(estabelecimentoRequest.getContato())
                .build();

        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);

        EstabelecimentoResponse response = estabelecimentoService.save(estabelecimentoRequest);

        assertNotNull(response);
        assertEquals("Estabelecimento 1", response.getNome());
    }

    @Test
    void testFindAllEstabelecimentos() {
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .id(1L)
                .nome("Estabelecimento 1")
                .endereco("Endereco 1")
                .contato("Contato 1")
                .build();

        when(estabelecimentoRepository.findAll()).thenReturn(Collections.singletonList(estabelecimento));

        assertEquals(1, estabelecimentoService.findAll().size());
    }

    @Test
    void testFindByIdEstabelecimento() {
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .id(1L)
                .nome("Estabelecimento 1")
                .endereco("Endereco 1")
                .contato("Contato 1")
                .build();

        when(estabelecimentoRepository.findById(1L)).thenReturn(Optional.of(estabelecimento));

        Optional<EstabelecimentoResponse> response = estabelecimentoService.findById(1L);

        assertTrue(response.isPresent());
        assertEquals("Estabelecimento 1", response.get().getNome());
    }

    @Test
    void testUpdateEstabelecimento() {
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .id(1L)
                .nome("Estabelecimento 1")
                .endereco("Endereco 1")
                .contato("Contato 1")
                .build();

        when(estabelecimentoRepository.findById(1L)).thenReturn(Optional.of(estabelecimento));
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);

        EstabelecimentoRequest updatedRequest = new EstabelecimentoRequest(null, "Estabelecimento Atualizado", "Endereco Atualizado", "Contato Atualizado");
        EstabelecimentoResponse response = estabelecimentoService.update(1L, updatedRequest);

        assertNotNull(response);
        assertEquals("Estabelecimento Atualizado", response.getNome());
    }

    @Test
    void testDeleteEstabelecimento() {
        doNothing().when(estabelecimentoRepository).deleteById(1L);

        assertDoesNotThrow(() -> estabelecimentoService.deleteById(1L));
        verify(estabelecimentoRepository, times(1)).deleteById(1L);
    }
}
