#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: JwtAuthRequest <br>
 * Pacote: ${package}.security.jwt.core.api.dto.request
 *
 * <p>Classe DTO (request) para receber as informações de autenticação (userName e password).</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Getter @Setter
public class JwtAuthRequest {
	private static final String USERNAME_REQUIRED = "O campo UserName é obrigatório";
	private static final String PASSWORD_REQUIRED = "O campo Senha é obrigatório";
	
	@NotBlank(message = USERNAME_REQUIRED)
	private String userName;
	
	@NotBlank(message = PASSWORD_REQUIRED)
	private String password;
}
