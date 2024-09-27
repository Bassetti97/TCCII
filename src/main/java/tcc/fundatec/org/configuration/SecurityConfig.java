package tcc.fundatec.org.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Criptografa a senha
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para simplicidade (não recomendado em produção)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/usuario/cadastrar").permitAll() // Permitir o registro de usuário sem autenticação
                        .requestMatchers("/login").permitAll() // Permitir acesso ao login
                        .anyRequest().authenticated() // Todas as outras rotas precisam de autenticação
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Página de login customizada
                        .defaultSuccessUrl("/agenda", true) // Redireciona após login
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll()); // Permite logout
        return http.build();
    }
}
