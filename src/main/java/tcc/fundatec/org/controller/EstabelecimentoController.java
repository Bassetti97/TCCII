package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimentoById(@PathVariable Long id) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findById(id);
        return estabelecimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estabelecimento createEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.save(estabelecimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> updateEstabelecimento(@PathVariable Long id, @RequestBody Estabelecimento estabelecimentoDetails) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findById(id);

        if (estabelecimento.isPresent()) {
            Estabelecimento updatedEstabelecimento = estabelecimento.get();
            updatedEstabelecimento.setRazaoSocial(estabelecimentoDetails.getRazaoSocial());
            updatedEstabelecimento.setCnpj(estabelecimentoDetails.getCnpj());
            updatedEstabelecimento.setLogradouro(estabelecimentoDetails.getLogradouro());
            updatedEstabelecimento.setContato(estabelecimentoDetails.getContato());
            // Atualizar outros campos conforme necessário...
            return ResponseEntity.ok(estabelecimentoService.save(updatedEstabelecimento));
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
