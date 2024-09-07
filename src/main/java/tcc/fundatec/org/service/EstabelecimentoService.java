package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.EstabelecimentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<Estabelecimento> findAll() {
        return estabelecimentoRepository.findAll();
    }

    public Optional<Estabelecimento> findById(Long id) {
        return estabelecimentoRepository.findById(id);
    }

    public Estabelecimento save(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }

    public void deleteById(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    public List<Estabelecimento> findByNome(String nome) {
        return estabelecimentoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
