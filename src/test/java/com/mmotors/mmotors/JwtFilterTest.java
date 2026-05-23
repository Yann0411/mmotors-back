package com.mmotors.mmotors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtServiceOld jwtService;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    void testSansHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtService, never()).tokenValide(any());
    }

    @Test
    void testTokenInvalide() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer tokeninvalide");
        when(jwtService.tokenValide("tokeninvalide")).thenReturn(false);

            jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(clientRepository, never()).findByEmail(any());


    }

    @Test
    void testTokenValide() throws Exception {
        Client client = new Client();
        client.setEmail("test@mail.com");

          client.setRole("CLIENT");

        when(request.getHeader("Authorization")).thenReturn("Bearer tokenvalide");

         when(jwtService.tokenValide("tokenvalide")).thenReturn(true);
        when(jwtService.extraireEmail("tokenvalide")).thenReturn("test@mail.com");

         when(clientRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(client));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
