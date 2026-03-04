#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import java.time.Duration;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import ${package}.security.jwt.config.properties.JwtProperties;
import ${package}.security.jwt.service.JwtCookieFactoryService;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtDevelopRefreshCookieFactoryServiceImpl<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.service.impl
 *
 * <p>Classe de serviço para implementar o contrato fornecido pela interface {@link JwtCookieFactoryService}.</p>
 * 
 * <p><strong>Deve ser utilizado nos profiles develop, homolog ou test.</strong></p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@Profile({ "develop", "homolog", "test" })
@RequiredArgsConstructor
public class JwtDevelopRefreshCookieFactoryServiceImpl implements JwtCookieFactoryService {
	private final JwtProperties jwtProperties;
	
	@Override
	public ResponseCookie create(String cookieName, String cookieValue, Boolean isLogout) {		
		// Retorna o Cookie HttpOnly (para Refresh Token) configurado para ambientes de desenvolvimento, homologação ou teste.
		return ResponseCookie.from(cookieName, cookieValue)
				.httpOnly(true)
				.secure(false) // false para localhost/HTTP, true para produção/HTTPS.
				.sameSite("Lax")
				.path("/auth") // Refresh token somente.
				.maxAge(Duration.ofMinutes(Boolean.TRUE.equals((isLogout)) ? 0 : jwtProperties.getJwt().getRefreshExpiration()))
				.build();
	}
}
