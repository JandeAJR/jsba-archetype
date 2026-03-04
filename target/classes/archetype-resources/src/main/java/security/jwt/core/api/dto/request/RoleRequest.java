#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: RoleRequest<br>
 * Pacote: ${package}.security.jwt.core.api.dto.request
 *
 * <p>Classe DTO (request) recebe as informações da role.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
@AllArgsConstructor
public class RoleRequest {
	private static final String ROLE_NAME_REQUIRED = "O campo Nome da Role é obrigatório";
	private static final String ROLE_NAME_SIZE = "O campo Nome da Role deve ter no mínimo 6 e no máximo 50 caracteres";
	private static final String ROLE_DESCRIPTION_REQUIRED = "O campo Descrição da Role é obrigatório";
	private static final String ROLE_DESCRIPTION_SIZE = "O campo Descrição da Role deve ter no mínimo 8 e no máximo 255 caracteres";
	
	@NotBlank(message = ROLE_NAME_REQUIRED)
	@Size(min = 6, max = 50, message = ROLE_NAME_SIZE)
	private String name;
	
	@NotBlank(message = ROLE_DESCRIPTION_REQUIRED)
	@Size(min = 8, max = 255, message = ROLE_DESCRIPTION_SIZE)
	private String description;
}
