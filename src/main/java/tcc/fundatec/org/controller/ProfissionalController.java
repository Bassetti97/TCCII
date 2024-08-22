package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.model.Profissional;
import tcc.fundatec.org.service.ProfissionalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping
    public List<Profissional> getAllProfissionais() {
        return profissionalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> getProfissionalById(@PathVariable Long id) {
        Optional<Profissional> profissional = profissionalService.findById(id);
        return profissional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Profissional createProfissional(@RequestBody Profissional profissional) {
        return profissionalService.save(profissional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> updateProfissional(@PathVariable Long id, @RequestBody Profissional profissionalDetails) {
        Optional<Profissional> profissional = profissionalService.findById(id);

        if (profissional.isPresent()) {
            Profissional updatedProfissional = profissional.get();
            updatedProfissional.setNome(profissionalDetails.getNome());
            updatedProfissional.setCpf(profissionalDetails.getCpf());
            updatedProfissional.setTelefone(profissionalDetails.getTelefone());
            updatedProfissional.setEmail(profissionalDetails.getEmail());
            updatedProfissional.setProfissao(profissionalDetails.getProfissao());
            // Atualizar outros campos conforme necessário...
            return ResponseEntity.ok(profissionalService.save(updatedProfissional));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfissional(@PathVariable Long id) {
        if (profissionalService.findById(id).isPresent()) {
            profissionalService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
