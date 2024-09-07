package tcc.fundatec.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.fundatec.org.model.Agendamento;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByClienteNomeContainingIgnoreCase(String nomeCliente);
}
