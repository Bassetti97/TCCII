package tcc.fundatec.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.fundatec.org.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
