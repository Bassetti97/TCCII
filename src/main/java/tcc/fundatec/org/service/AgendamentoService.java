package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.controller.request.AgendamentoRequest;
import tcc.fundatec.org.controller.response.AgendamentoResponse;
import tcc.fundatec.org.controller.response.ClienteResponse;
import tcc.fundatec.org.controller.response.EstabelecimentoResponse;
import tcc.fundatec.org.model.Agendamento;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.AgendamentoRepository;
import tcc.fundatec.org.repository.ClienteRepository;
import tcc.fundatec.org.repository.EstabelecimentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<AgendamentoResponse> findAll() {
        return agendamentoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<AgendamentoResponse> findById(Long id) {
        return agendamentoRepository.findById(id)
                .map(this::toResponse);
    }

    public AgendamentoResponse save(AgendamentoRequest request) {
        if (request.getClienteId() == null || request.getEstabelecimentoId() == null) {
            throw new RuntimeException("Cliente ID e Estabelecimento ID não podem ser nulos");
        }
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        Agendamento agendamento = Agendamento.builder()
                .dataHorario(request.getDataHorario())
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .build();

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        return toResponse(savedAgendamento);
    }

    public void deleteById(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public List<AgendamentoResponse> findByClienteNome(String nomeCliente) {
        return agendamentoRepository.findByClienteNomeContainingIgnoreCase(nomeCliente).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AgendamentoResponse update(Long id, AgendamentoRequest request) {
        try {
            // Verifica se o agendamento existe
            Agendamento existingAgendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

            // Atualiza o agendamento com os dados do request
            existingAgendamento.setDataHorario(request.getDataHorario());
            existingAgendamento.setCliente(clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
            existingAgendamento.setEstabelecimento(estabelecimentoRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado")));

            // Salva o agendamento atualizado
            Agendamento updatedAgendamento = agendamentoRepository.save(existingAgendamento);

            // Converte e retorna a resposta
            return AgendamentoResponse.builder()
                    .id(updatedAgendamento.getId())
                    .dataHorario(updatedAgendamento.getDataHorario())
                    .cliente(ClienteResponse.builder()
                            .id(updatedAgendamento.getCliente().getId())
                            .nome(updatedAgendamento.getCliente().getNome())
                            .build())
                    .estabelecimento(EstabelecimentoResponse.builder()
                            .id(updatedAgendamento.getEstabelecimento().getId())
                            .nome(updatedAgendamento.getEstabelecimento().getNome())
                            .build())
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar o agendamento: " + e.getMessage());
        }
    }


    private AgendamentoResponse toResponse(Agendamento agendamento) {
        return AgendamentoResponse.builder()
                .id(agendamento.getId())
                .dataHorario(agendamento.getDataHorario())
                .cliente(ClienteResponse.builder()
                        .id(agendamento.getCliente().getId())
                        .nome(agendamento.getCliente().getNome())
                        .build())
                .estabelecimento(EstabelecimentoResponse.builder()
                        .id(agendamento.getEstabelecimento().getId())
                        .nome(agendamento.getEstabelecimento().getNome())
                        .build())
                .build();
    }
}
