package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.service.EstabelecimentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public List<Estabelecimento> getAllEstabelecimentos() {
        return estabelecimentoService.findAll();
    }

    @GetMapping("/buscarpornome")
    public List<Estabelecimento> searchEstabelecimentoByName(@RequestParam String nome) {
        return estabelecimentoService.findByNome(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimentoById(@PathVariable Long id) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findById(id);
        return estabelecimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estabelecimento> createEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        Estabelecimento created = estabelecimentoService.save(estabelecimento);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> updateEstabelecimento(@PathVariable Long id, @RequestBody Estabelecimento estabelecimentoDetails) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findById(id);
        if (estabelecimento.isPresent()) {
            Estabelecimento updated = estabelecimento.get();
            updated.setNome(estabelecimentoDetails.getNome());
            updated.setEndereco(estabelecimentoDetails.getEndereco());
            updated.setContato(estabelecimentoDetails.getContato());
            return ResponseEntity.ok(estabelecimentoService.save(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable Long id) {
        if (estabelecimentoService.findById(id).isPresent()) {
            estabelecimentoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
