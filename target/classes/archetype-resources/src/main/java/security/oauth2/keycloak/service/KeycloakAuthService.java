#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.service;

import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakTokensResponse;

/**
 * Interface: KeycloakAuthService <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.service
 *
 * <p>Interface para fornecer o contrato com os serviços de autenticação no Keycloak.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface KeycloakAuthService {
	KeycloakTokensResponse exchangeCodeForToken(String code);
	KeycloakTokensResponse refreshToken(String refreshToken);
	void logoutKeycloak(String refreshToken);
}
