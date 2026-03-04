#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.infrastructure.service.impl;

import java.time.Duration;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import ${package}.application.shared.util.Const;
import ${package}.security.oauth2.keycloak.config.properties.KeycloakProperties;
import ${package}.security.oauth2.keycloak.service.KeycloakCookieFactoryService;
import lombok.RequiredArgsConstructor;

/**
 * Classe: KeycloakDevelopCookieFactoryServiceImpl<br>
 * Pacote: ${package}.security.oauth2.keycloak.service.impl
 *
 * <p>Classe de serviço para implementar o contrato fornecido pela interface {@link KeycloakCookieFactoryService}.</p>
 *
 *<p><strong>Deve ser utilizado nos profiles develop, homolog ou test.</strong></p>
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@Profile({ "develop", "homolog", "test" })
@RequiredArgsConstructor
public class KeycloakDevelopCookieFactoryServiceImpl implements KeycloakCookieFactoryService {
	private final KeycloakProperties keycloakProperties;
	
	@Override
	public ResponseCookie create(String cookieName, String cookieValue, Boolean isLogout) {		
		// Refresh token = "/auth", access token = "/".
		String path = (cookieName.equals(Const.REFRESH_TOKEN_COOKIE_NAME)) ? "/auth" : "/";
		
		// Refresh token = tempo de expiração longo, access token = tempo de expiração curto.
		Long expiration = (cookieName.equals(Const.REFRESH_TOKEN_COOKIE_NAME)) ? 
				keycloakProperties.getRefreshExpiration() :
					keycloakProperties.getExpiration();
		
		// Se for uma requisição de logout, então maxAge = 0 para exclusão do cookie no navegador do usuário.
		Long maxAge = (Boolean.TRUE.equals(isLogout)) ? 0 : expiration;
				
		// Retorna o Cookie HttpOnly configurado para ambientes de desenvolvimento, homologação ou teste.
		return ResponseCookie.from(cookieName, cookieValue)
				.httpOnly(true)
				.secure(false) // false para localhost/HTTP, true para produção/HTTPS.
				.sameSite("Lax")
                .path(path)
                .maxAge(Duration.ofMinutes(maxAge))
                .build();
	}
}
