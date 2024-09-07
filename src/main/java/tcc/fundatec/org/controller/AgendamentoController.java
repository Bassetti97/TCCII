package tcc.fundatec.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.model.Agendamento;
import tcc.fundatec.org.service.AgendamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoService.findAll();
    }

    @GetMapping("/buscarpornome")
    public List<Agendamento> searchAgendamentosByClienteName(@RequestParam String nomeCliente) {
        return agendamentoService.findByClienteNome(nomeCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable Long id) {
        Optional<Agendamento> agendamento = agendamentoService.findById(id);
        return agendamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agendamento> createAgendamento(@RequestBody Agendamento agendamento) {
            Agendamento savedAgendamento = agendamentoService.save(agendamento);
            return ResponseEntity.ok(savedAgendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> updateAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamentoDetails) {
        Optional<Agendamento> agendamento = agendamentoService.findById(id);

        if (agendamento.isPresent()) {
            Agendamento updatedAgendamento = agendamento.get();
            updatedAgendamento.setDataHorario(agendamentoDetails.getDataHorario());
            updatedAgendamento.setCliente(agendamentoDetails.getCliente());
            updatedAgendamento.setEstabelecimento(agendamentoDetails.getEstabelecimento());
            return ResponseEntity.ok(agendamentoService.save(updatedAgendamento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        if (agendamentoService.findById(id).isPresent()) {
            agendamentoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
