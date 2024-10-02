package tcc.fundatec.org.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import tcc.fundatec.org.controller.request.CadastrarLoginRequest;
import tcc.fundatec.org.controller.request.LoginRequest;

import tcc.fundatec.org.controller.response.LoginResponse;
import tcc.fundatec.org.model.User;
import tcc.fundatec.org.repository.UserRepository;
import tcc.fundatec.org.service.security.TokenService;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(loginRequest.senha(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity registrar(@RequestBody CadastrarLoginRequest loginRequest) {
        Optional<User> user = this.userRepository.findByEmail(loginRequest.email());

       if (user.isEmpty()){
           User newUser = new User();
           newUser.setSenha(passwordEncoder.encode(loginRequest.senha()));
           newUser.setEmail(loginRequest.email());
           this.userRepository.save(newUser);

           String token = this.tokenService.generateToken(newUser);
           return ResponseEntity.ok(new LoginResponse(token));
       }

        return ResponseEntity.badRequest().build();
    }
}
