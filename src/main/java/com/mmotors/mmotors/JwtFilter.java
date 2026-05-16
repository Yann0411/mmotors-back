package com.mmotors.mmotors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClientRepository clientRepository;

    public JwtFilter(JwtService jwtService, ClientRepository clientRepository) {

        System.out.println("=>=>=>=>=>=>JE SUIS DANS JWTFILTER<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>J'injecte jwtService et ClientRepository<=<=<=<=<=<=<=<=<=");
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");


        System.out.println("=>=>=>=>=>=>JE SUIS DANS JWTFILTER<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>=>URL appelée: " + request.getRequestURI() + "<=<=<=<=<=<=<=<=<=<=<=<");
        System.out.println("=>=>=>=>=>=>=>Authorisation header : "+ header + "<=<=<=<=<=<=<=<=<=");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.tokenValide(token)) {
                String email = jwtService.extraireEmail(token);
                Client client = clientRepository.findByEmail(email).orElse(null);
                if (client != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null,
                                    List.of(new SimpleGrantedAuthority(client.getRole())));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }



        filterChain.doFilter(request, response);
    }
}
