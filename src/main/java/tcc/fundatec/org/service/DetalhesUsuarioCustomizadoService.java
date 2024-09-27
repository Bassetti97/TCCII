package tcc.fundatec.org.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tcc.fundatec.org.model.Usuario;
import tcc.fundatec.org.repository.UsuarioRepository;

import java.util.stream.Collectors;

@Service
public class DetalhesUsuarioCustomizadoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DetalhesUsuarioCustomizadoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(usuario.getRoles().stream()
                        .map(role -> "ROLE_" + role)
                        .collect(Collectors.toList())
                        .toArray(new String[0]))
                .build();
    }
}
