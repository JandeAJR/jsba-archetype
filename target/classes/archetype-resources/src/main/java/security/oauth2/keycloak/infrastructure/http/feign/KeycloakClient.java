#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.infrastructure.http.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakTokensResponse;

/**
 * Interface: KeycloakClient <br>
 * Pacote: ${package}.security.oauth2.keycloak.infrastructure.http.feign
 *
 * <p>Interface Open Feign Client para fornecer o método de login no Keycloak.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@FeignClient(name = "keycloak-client", url = "${symbol_dollar}{security.oauth2.keycloak.issuer:http://localhost:8080/realms/develop}")
public interface KeycloakClient {
	@PostMapping(value = "/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KeycloakTokensResponse getTokens(Map<String, ?> params);
	
	@PostMapping(value = "/protocol/openid-connect/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	void logout(Map<String, ?> params);
}
