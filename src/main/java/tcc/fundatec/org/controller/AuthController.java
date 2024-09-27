package tcc.fundatec.org.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tcc.fundatec.org.model.Usuario;
import tcc.fundatec.org.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return new ResponseEntity<>("Não autenticado", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Usuário autenticado: " + auth.getName(), HttpStatus.OK);
    }
}
