#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ${package}.security.jwt.config.properties.JwtProperties;
import ${package}.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtAuthenticationFilter <br>
 * Pacote: ${package}.security.jwt.core.filter
 *
 * <p>Classe reponsável por implementar o filtro de autenticação JWT que intercepta requisições HTTP para validar tokens JWT. <br>
 * Realiza a extração do token de cabeçalho Authorization, faz a validação do token 
 * e autentica o usuário no contexto de segurança do Spring</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProperties jwtProperties; // Propriedades mapeadas do arquivo application-jwt.yml.
    private final JwtService jwtService; // Serviço para manipulação de tokens JWT.
    private final UserDetailsService userDetailsService; // Serviço para carregar detalhes do usuário (faz parte do Spring Security).
    
    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

    	// Este método extrai o token JWT do cabeçalho Authorization da requisição HTTP,
    	// valida o token, extrai o nome de usuário e autentica o usuário no contexto de segurança do Spring.
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String prefix = "Bearer ";

        if (authHeader != null && authHeader.startsWith(prefix)) { 
            String token = authHeader.replace(prefix, "").trim(); // Remove o prefixo "Bearer " do início do cabeçalho. 
            String username = jwtService.extractUsername(token); // Extrai o nome de usuário do token.

            // Carrega os detalhes do usuário usando o UserDetailsService (consulta o repositório de dados).
            UserDetails user =
                    userDetailsService.loadUserByUsername(username);

            // Cria um objeto de autenticação com os detalhes do usuário.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user, // principal
                            null, // credentials
                            user.getAuthorities() // authorities
                    );

            // Define o objeto de autenticação no contexto de segurança do Spring.
            SecurityContextHolder
                    .getContext() // Obtém o contexto de segurança atual (Spring Boot Context)
                    .setAuthentication(authentication); // Define a autenticação
        }

        // Continua a cadeia de filtros.
        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
    	// Método para determinar se o filtro deve ser aplicado ou não.
    	String path = request.getServletPath();
    	return jwtProperties.getAuth().getLoginPath().equals(path);
    }
}
