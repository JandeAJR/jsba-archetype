#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.role.output;

import java.util.UUID;

import ${package}.security.jwt.core.domain.model.Role;

/**
 * Record: RoleOutput<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.role.output
 * 
 * <p>Record para representar os parâmetros de saída para os casos de uso do domínio Role.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public record RoleOutput(
		UUID id,
		String name,
		String description
) {
	public static RoleOutput fromDomain(Role role) {
		return new RoleOutput(
				role.getId(), 
				role.getName(), 
				role.getDescription());
	}
}
