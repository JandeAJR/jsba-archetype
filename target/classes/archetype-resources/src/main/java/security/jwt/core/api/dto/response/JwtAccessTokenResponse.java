#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: JwtTokensResponse <br>
 * Pacote: ${package}.security.jwt.core.api.dto.response
 *
 * <p>Classe DTO (response) para retornar o token de acesso (access token).</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Getter @Setter
@AllArgsConstructor
public class JwtAccessTokenResponse {
	private String token;
}
