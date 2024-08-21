package tcc.fundatec.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.fundatec.org.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
