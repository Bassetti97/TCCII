package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.controller.request.EstabelecimentoRequest;
import tcc.fundatec.org.controller.response.EstabelecimentoResponse;
import tcc.fundatec.org.service.EstabelecimentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @PostMapping
    public ResponseEntity<EstabelecimentoResponse> save(@RequestBody EstabelecimentoRequest request) {
        EstabelecimentoResponse estabelecimentoResponse = estabelecimentoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponse> update(@PathVariable Long id, @RequestBody EstabelecimentoRequest request) {
        try {
            EstabelecimentoResponse estabelecimentoResponse = estabelecimentoService.update(id, request);
            return ResponseEntity.ok(estabelecimentoResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoResponse>> findAll() {
        List<EstabelecimentoResponse> estabelecimentos = estabelecimentoService.findAll();
        return ResponseEntity.ok(estabelecimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponse> findById(@PathVariable Long id) {
        Optional<EstabelecimentoResponse> estabelecimento = estabelecimentoService.findById(id);
        return estabelecimento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            estabelecimentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<EstabelecimentoResponse>> findByNome(@PathVariable String nome) {
        List<EstabelecimentoResponse> estabelecimentos = estabelecimentoService.findByNome(nome);
        return ResponseEntity.ok(estabelecimentos);
    }
}
