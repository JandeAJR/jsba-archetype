#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.infrastructure.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ${package}.security.oauth2.keycloak.config.properties.KeycloakProperties;
import ${package}.security.oauth2.keycloak.infrastructure.http.feign.KeycloakClient;
import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakTokensResponse;
import ${package}.security.oauth2.keycloak.service.KeycloakAuthService;
import lombok.RequiredArgsConstructor;

/**
 * Classe: KeycloakAuthServiceImpl <br>
 * Pacote: ${package}.security.oauth2.keycloak.modules.oauth2.infrastructure.service.impl
 *
 * <p>Classe reponsável por implementar o contrato com os serviços de autenticação no Keycloak. <br>
 * Implementa a interface {@link KeycloakAuthService}. <br>
 * Utiliza o cliente http open feign através da interface {@link KeycloakClient}.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class KeycloakAuthServiceImpl implements KeycloakAuthService {
	private final KeycloakProperties keycloakProperties;
	private final KeycloakClient keycloakClient;
	
	@Override
	public KeycloakTokensResponse exchangeCodeForToken(String code) {
	    Map<String, String> params = new HashMap<>();
	    params.put("grant_type", "authorization_code");
	    params.put("code", code);
	    params.put("client_id", keycloakProperties.getKeycloak().getClientId());
	    params.put("client_secret", keycloakProperties.getKeycloak().getClientSecret());
	    params.put("redirect_uri", keycloakProperties.getKeycloak().getRedirectUri()); // Deve ser IGUAL a que foi cadastrada no Keycloak

	    return keycloakClient.getTokens(params);
	}
	
	@Override
	public KeycloakTokensResponse refreshToken(String refreshToken) {
	    Map<String, String> params = new HashMap<>();
	    params.put("grant_type", "refresh_token");
	    params.put("refresh_token", refreshToken);
	    params.put("client_id", keycloakProperties.getKeycloak().getClientId());
	    params.put("client_secret", keycloakProperties.getKeycloak().getClientSecret());

	    return keycloakClient.getTokens(params);
	}
	
	@Override
	public void logoutKeycloak(String refreshToken) {
	    Map<String, String> params = new HashMap<>();
	    params.put("client_id", keycloakProperties.getKeycloak().getClientId());
	    params.put("client_secret", keycloakProperties.getKeycloak().getClientSecret());
	    params.put("refresh_token", refreshToken);

	    keycloakClient.logout(params);
	}
}
