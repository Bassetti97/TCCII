package tcc.fundatec.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcc.fundatec.org.model.Cliente;
import tcc.fundatec.org.model.Estabelecimento;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Optional<Estabelecimento> findByNomeContainingIgnoreCase(String nome);
}
