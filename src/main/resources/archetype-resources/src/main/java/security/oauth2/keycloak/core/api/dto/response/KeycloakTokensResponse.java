#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.api.dto.response;

import lombok.Data;

/**
 * Classe: KeycloakTokensResponse <br>
 * Pacote: ${package}.security.oauth2.keycloak.modules.oauth2.api.dto.response
 *
 * <p>Classe DTO (response) para retornar os tokens de acesso (access token e refresh token) gerados pelo Keycloak.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Data
public class KeycloakTokensResponse {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String tokenType;
}
