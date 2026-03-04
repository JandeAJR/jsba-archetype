#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ${package}.security.jwt.config.properties.JwtProperties;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtServiceImpl <br>
 * Pacote: ${package}.security.jwt.core.service
 *
 * <p>Classe reponsável por implementar o serviço JWT que gera e valida tokens JWT. <br>
 * Realiza a criação dos tokens JWT assinados com uma chave secreta e extrai informações dos tokens. <br>
 * Implementa a interface {@link JwtService}.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
	private final JwtProperties jwtProperties; // Propriedades mapeadas do arquivo application-jwt.yml

    private SecretKey secretKey; // Chave secreta para assinar os tokens JWT

    @PostConstruct
    private void init() {
    	// Método chamado após a injeção de dependências para inicializar o jwtKey
    	this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getJwt().getKey())); // Decodifica a chave secreta em Base64 e cria a chave HMAC SHA.
    }                                                                                                // HMAC SHA é um algoritmo de assinatura usado para garantir 
    	                                                                                            // a integridade e autenticidade dos tokens JWT.

    // Extrai todas as clains contidas no token
 	private Claims extractAllClaims(String token) {
 		return Jwts.parser()          // cria o parser
 	        .verifyWith(secretKey)    // configura chave para verificar assinatura
 	        .build()                  // constrói parser
 	        .parseSignedClaims(token) // parse do token assinado
 	        .getPayload();            // pega o payload -> Claims
 	}
 	
 	// Método genérico para extrair qualquer claim
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
	}
    
    @Override
    public String generateAccessToken(UserEntity userEntity) {
    	// Método para gerar um token JWT para o usuário fornecido
        long expirationMillis = jwtProperties.getJwt().getExpiration() * 60 * 1000; // Converte minutos para milissegundos

        // Cria o token JWT com o userName, id, name e roles de usuário, data de emissão e data de expiração
        return Jwts.builder()
            .subject(userEntity.getUsername())                          // Define o userName de usuário (subject) do token
            .claim("userId", userEntity.getId())                        // Id do usuário
            .claim("email", userEntity.getEmail())                      // Email do usuário
            .claim("name", userEntity.getName())                        // Email do usuário
            .claim("accountNonLocked", userEntity.isAccountNonLocked()) // Se a conta de usuário está bloqueada
            .claim("enabled", userEntity.isEnabled())                   // Se o usuário está habilitado
            .claim(
                "roles", // Role do usuário
                userEntity.getAuthorities()
	                	.stream()
	                	.map(GrantedAuthority::getAuthority)
	                	.toList()
            )
            .issuedAt(new Date()) // Define a data de emissão do token
            .expiration(new Date(System.currentTimeMillis() + expirationMillis)) // Define a data de expiração do token (em milissegundos)
            .signWith(secretKey)  // Assina o token com a chave secreta            
            .compact();           // Constrói o token JWT como uma string compacta
    }
    
    @Override
    public String generateRefreshToken(String userName) {
    	// Método para gerar um refresh token JWT para o usuário fornecido
        long expirationMillis = jwtProperties.getJwt().getRefreshExpiration() * 60 * 1000; // Converte minutos para milissegundos

        return Jwts.builder()
            .subject(userName)                                                   // Define o userName de usuário (subject) do token
            .claim("type", "refresh")                                            // Tipo do token: refresh token
            .issuedAt(new Date())                                                // Define a data de emissão do token
            .expiration(new Date(System.currentTimeMillis() + expirationMillis)) // Define a data de expiração do token (em milissegundos)
            .signWith(secretKey)                                                 // Assina o token com a chave secreta 
            .compact();                                                          // Constrói o token JWT como uma string compacta
    }
	
    @Override
    public String extractUserId(String token) {
    	return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    @Override
	public String extractUsername(String token) {
		// Método específico para o username (subject)
	    return extractClaim(token, Claims::getSubject);
	}
		
    @Override
    public String extractEmail(String token) {
    	return extractClaim(token, claims -> claims.get("email", String.class));
    }

    @Override
	public String extractName(String token) {
	    return extractClaim(token, claims -> claims.get("name", String.class));
	}
	
    @Override
	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
	    return extractClaim(token, claims -> claims.get("roles", List.class));
	}
}
