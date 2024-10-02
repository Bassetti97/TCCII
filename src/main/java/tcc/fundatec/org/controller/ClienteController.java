package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.controller.request.ClienteRequest;
import tcc.fundatec.org.controller.response.ClienteResponse;
import tcc.fundatec.org.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest request) {
        ClienteResponse clienteResponse = clienteService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @RequestBody ClienteRequest request) {
        try {
            ClienteResponse clienteResponse = clienteService.update(id, request);
            return ResponseEntity.ok(clienteResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {
        List<ClienteResponse> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable Long id) {
        Optional<ClienteResponse> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClienteResponse>> findByNome(@PathVariable String nome) {
        List<ClienteResponse> clientes = clienteService.findByNome(nome);
        return ResponseEntity.ok(clientes);
    }
}
