package tcc.fundatec.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.model.Agendamento;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;
import tcc.fundatec.org.repository.AgendamentoRepository;
import tcc.fundatec.org.repository.ClienteRepository;
import tcc.fundatec.org.repository.EstabelecimentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    private ClienteRepository clienteRepository;
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> findById(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento save(Agendamento agendamento) {

        Cliente cliente = new Cliente();
        Estabelecimento estabelecimento = new Estabelecimento();

        Agendamento agendamentos = new Agendamento();
        agendamentos.setDataHorario(agendamento.getDataHorario());
        agendamentos.setCliente(cliente);
        agendamentos.setEstabelecimento(estabelecimento);

        // Salve o agendamento
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public List<Agendamento> findByClienteNome(String nomeCliente) {
        return agendamentoRepository.findByClienteNomeContainingIgnoreCase(nomeCliente);
    }
}
