package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
        private JwtService jwtService;

    @Test
    void testgenererToken() {

        String token = jwtService.genererToken("test@mail.com");
        assertNotNull(token);
    }

    @Test
    void extraireEmail() {

        String token = jwtService.genererToken("test@mail.com");
            String email = jwtService.extraireEmail(token);
        assertEquals("test@mail.com", email);
    }

    @Test
    void tokenValide() {
        String token = jwtService.genererToken("test@mail.com");

        assertTrue(jwtService.tokenValide(token));

    }


    @Test
    void tokenInvalide() {
        assertFalse(jwtService.tokenValide("token.invalide.bidon"));
    }

}
