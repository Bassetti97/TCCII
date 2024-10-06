package tcc.fundatec.org.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tcc.fundatec.org.model.User;
import tcc.fundatec.org.service.security.TokenService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um objeto User para os testes
        user = new User();
        user.setEmail("test@example.com");
        user.setSenha("123456");
    }

    @Test
    void testGenerateToken() {
        String token = tokenService.generateToken(user);
        assertNotNull(token);
        // Verifica se o token é uma string JWT válida, sem a string "Bearer"
        assertFalse(token.contains("Bearer"));
    }

    @Test
    void testValidateToken() {
        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);
        assertEquals(user.getEmail(), subject);
    }

    @Test
    void testValidateInvalidToken() {
        String subject = tokenService.validateToken("invalid_token");
        assertNull(subject);
    }
}
