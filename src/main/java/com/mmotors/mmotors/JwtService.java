package com.mmotors.mmotors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;



@Service
public class JwtService {

          private static final String SECRET_KEY = "mmotors-bachelor-developpeur-application-yann-2026-bloc3";

    public String genererToken(String email) {

        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS JWTSERVICE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>/GENERER_TOKEN<=<=<=<=<=<=<<=<=<=<=<=<=<=<=");
        Date maintenant = new Date();
         Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        return Jwts.builder()
                 .subject(email)
                .issuedAt(maintenant)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

       public String extraireEmail(String token) {

           System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS JWTSERVICE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
           System.out.println("=>=>=>=>=>=>=>=>=>EXTRAIRE_EMAIL<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        var claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return claims.getSubject();
    }

    public boolean tokenValide(String token) {

        System.out.println("=>=>=>=>=>=>=>=>=>TOKEN VALIDE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        try {
            System.out.println("=>=>=>=>=>=>=>=>=TOKEN TRUE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");

            extraireEmail(token);
            return true;
        } catch (Exception e) {
            System.out.println("=>=>=>=>=>=>=>=>=>TOKEN FALSE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
            return false;
        }
    }
}
