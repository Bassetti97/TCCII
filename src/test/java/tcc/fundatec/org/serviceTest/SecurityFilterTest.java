package tcc.fundatec.org.serviceTest;
/**
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.filter.OncePerRequestFilter;
import tcc.fundatec.org.model.User;
import tcc.fundatec.org.repository.UserRepository;
import tcc.fundatec.org.service.security.SecurityFilter;
import tcc.fundatec.org.service.security.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void shouldAuthenticateUserWithValidToken() throws ServletException, IOException {
        // Configurando o token e login simulados
        String token = "validToken";
        String userEmail = "user@example.com";
        User user = new User(1L, userEmail, "password");

        // Simulando comportamento do TokenService e UserRepository
        when(tokenService.validateToken(token)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Definindo o header de autorização
        request.addHeader("Authorization", "Bearer " + token);

        // Executando o filtro
        securityFilter.doFilterInternal(request, response, filterChain);

        // Verificando se o usuário foi autenticado corretamente
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(user, authentication.getPrincipal());

        // Verifica se o próximo filtro na cadeia foi chamado
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidToken() throws ServletException, IOException {
        // Simulando token inválido
        String invalidToken = "invalidToken";

        // Simulando comportamento do TokenService para token inválido
        when(tokenService.validateToken(invalidToken)).thenReturn(null);

        // Definindo o header de autorização
        request.addHeader("Authorization", "Bearer " + invalidToken);

        // Executando o filtro
        securityFilter.doFilterInternal(request, response, filterChain);

        // Verificando que a autenticação não foi definida
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);

        // Verifica se o próximo filtro na cadeia foi chamado
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateIfNoAuthorizationHeader() throws ServletException, IOException {
        // Executando o filtro sem header de autorização
        securityFilter.doFilterInternal(request, response, filterChain);

        // Verificando que a autenticação não foi definida
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);

        // Verifica se o próximo filtro na cadeia foi chamado
        verify(filterChain).doFilter(request, response);
    }
}
*/