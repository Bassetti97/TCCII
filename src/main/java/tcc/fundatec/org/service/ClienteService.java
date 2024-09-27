package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.controller.request.ClienteRequest;
import tcc.fundatec.org.controller.response.ClienteResponse;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ClienteResponse> findById(Long id) {
        return clienteRepository.findById(id).map(this::toResponse);
    }

    public ClienteResponse save(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .cpf(request.getCpf())
                .dataNascimento(request.getDataNascimento())
                .logradouro(request.getLogradouro())
                .complemento(request.getComplemento())
                .cep(request.getCep())
                .telefone(request.getTelefone())
                .email(request.getEmail())
                .build();

        Cliente savedCliente = clienteRepository.save(cliente);
        return toResponse(savedCliente);
    }

    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        existingCliente.setNome(request.getNome());
        existingCliente.setCpf(request.getCpf());
        existingCliente.setDataNascimento(request.getDataNascimento());
        existingCliente.setLogradouro(request.getLogradouro());
        existingCliente.setComplemento(request.getComplemento());
        existingCliente.setCep(request.getCep());
        existingCliente.setTelefone(request.getTelefone());
        existingCliente.setEmail(request.getEmail());

        Cliente updatedCliente = clienteRepository.save(existingCliente);
        return toResponse(updatedCliente);
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public List<ClienteResponse> findByNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .logradouro(cliente.getLogradouro())
                .complemento(cliente.getComplemento())
                .cep(cliente.getCep())
                .telefone(cliente.getTelefone())
                .email(cliente.getEmail())
                .build();
    }
}
