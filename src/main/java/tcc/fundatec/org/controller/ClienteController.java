package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isPresent()) {
            Cliente updatedCliente = cliente.get();
            updatedCliente.setNome(clienteDetails.getNome());
            updatedCliente.setCpf(clienteDetails.getCpf());
            updatedCliente.setDataNascimento(clienteDetails.getDataNascimento());
            updatedCliente.setLogradouro(clienteDetails.getLogradouro());
            updatedCliente.setComplemento(clienteDetails.getComplemento());
            updatedCliente.setCep(clienteDetails.getCep());
            updatedCliente.setTelefone(clienteDetails.getTelefone());
            updatedCliente.setEmail(clienteDetails.getEmail());
            return ResponseEntity.ok(clienteService.save(updatedCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteService.findById(id).isPresent()) {
            clienteService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarpornome")
    public List<Cliente> searchClientesByName(@RequestParam String nome) {
        return clienteService.findByNome(nome);
    }
}
