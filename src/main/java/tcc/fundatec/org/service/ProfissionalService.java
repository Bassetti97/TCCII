package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.model.Profissional;
import tcc.fundatec.org.repository.ProfissionalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<Profissional> findAll() {
        return profissionalRepository.findAll();
    }

    public Optional<Profissional> findById(Long id) {
        return profissionalRepository.findById(id);
    }

    public Profissional save(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public void deleteById(Long id) {
        profissionalRepository.deleteById(id);
    }

}
