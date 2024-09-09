package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.controller.request.AgendamentoRequest;
import tcc.fundatec.org.controller.response.AgendamentoResponse;
import tcc.fundatec.org.service.AgendamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody AgendamentoRequest request) {
        try {
            AgendamentoResponse agendamentoResponse = agendamentoService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoResponse);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao salvar o agendamento: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * FAZER O TESTE PRA VER SE RODA!!!
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> update(@PathVariable Long id, @RequestBody AgendamentoRequest request) {
        try {
            AgendamentoResponse agendamentoResponse = agendamentoService.update(id, request);
            return ResponseEntity.ok(agendamentoResponse);
        } catch (RuntimeException e) {
            // Retorna um erro
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> findAll() {
        List<AgendamentoResponse> agendamentos = agendamentoService.findAll();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> findById(@PathVariable Long id) {
        Optional<AgendamentoResponse> agendamento = agendamentoService.findById(id);
        return agendamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            agendamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{nomeCliente}")
    public ResponseEntity<List<AgendamentoResponse>> findByClienteNome(@PathVariable String nomeCliente) {
        List<AgendamentoResponse> agendamentos = agendamentoService.findByClienteNome(nomeCliente);
        return ResponseEntity.ok(agendamentos);
    }


}
