package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.controller.request.AgendamentoRequest;
import tcc.fundatec.org.controller.response.*;
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
        if (request.getClienteNome() == null || request.getEstabelecimentoNome() == null) {
            throw new RuntimeException("Cliente Nome e Estabelecimento Nome não podem ser nulos");
        }

        Cliente cliente = clienteRepository.findByNomeContainingIgnoreCase(request.getClienteNome())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Estabelecimento estabelecimento = estabelecimentoRepository.findByNomeContainingIgnoreCase(request.getEstabelecimentoNome())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        Agendamento agendamento = Agendamento.builder()
                .dataHorario(request.getDataHorario())
                .tipoServico(request.getTipoServico())
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
        Agendamento existingAgendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        existingAgendamento.setDataHorario(request.getDataHorario());
        existingAgendamento.setTipoServico(request.getTipoServico());
        existingAgendamento.setCliente(clienteRepository.findByNomeContainingIgnoreCase(request.getClienteNome())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        existingAgendamento.setEstabelecimento(estabelecimentoRepository.findByNomeContainingIgnoreCase(request.getEstabelecimentoNome())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado")));

        Agendamento updatedAgendamento = agendamentoRepository.save(existingAgendamento);

        return toResponse(updatedAgendamento);
    }

    private AgendamentoResponse toResponse(Agendamento agendamento) {
        return AgendamentoResponse.builder()
                .id(agendamento.getId())
                .dataHorario(agendamento.getDataHorario())
                .tipoServico(agendamento.getTipoServico())
                .cliente(AgendamentoClienteResponse.builder()
                        .id(agendamento.getCliente().getId())
                        .nome(agendamento.getCliente().getNome())
                        .build())
                .estabelecimento(AgendamentoEstabelecimentoResponse.builder()
                        .id(agendamento.getEstabelecimento().getId())
                        .nome(agendamento.getEstabelecimento().getNome())
                        .build())
                .build();
    }
}
