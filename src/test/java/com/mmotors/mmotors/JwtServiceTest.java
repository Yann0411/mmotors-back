package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtServiceOld jwtService = new JwtServiceOld();

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
