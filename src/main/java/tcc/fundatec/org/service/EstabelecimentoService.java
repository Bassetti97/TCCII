package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.controller.request.EstabelecimentoRequest;
import tcc.fundatec.org.controller.response.EstabelecimentoResponse;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.EstabelecimentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<EstabelecimentoResponse> findAll() {
        return estabelecimentoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<EstabelecimentoResponse> findById(Long id) {
        return estabelecimentoRepository.findById(id).map(this::toResponse);
    }

    public EstabelecimentoResponse save(EstabelecimentoRequest request) {
        Estabelecimento estabelecimento = Estabelecimento.builder()
                .nome(request.getNome())
                .endereco(request.getEndereco())
                .contato(request.getContato())
                .build();

        Estabelecimento savedEstabelecimento = estabelecimentoRepository.save(estabelecimento);
        return toResponse(savedEstabelecimento);
    }

    public EstabelecimentoResponse update(Long id, EstabelecimentoRequest request) {
        Estabelecimento existingEstabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        existingEstabelecimento.setNome(request.getNome());
        existingEstabelecimento.setEndereco(request.getEndereco());
        existingEstabelecimento.setContato(request.getContato());

        Estabelecimento updatedEstabelecimento = estabelecimentoRepository.save(existingEstabelecimento);
        return toResponse(updatedEstabelecimento);
    }

    public void deleteById(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    public List<EstabelecimentoResponse> findByNome(String nome) {
        return estabelecimentoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private EstabelecimentoResponse toResponse(Estabelecimento estabelecimento) {
        return EstabelecimentoResponse.builder()
                .id(estabelecimento.getId())
                .nome(estabelecimento.getNome())
                .endereco(estabelecimento.getEndereco())
                .contato(estabelecimento.getContato())
                .build();
    }
}
