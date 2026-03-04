#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.role.input;

/**
 * Record: RoleInput<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.role.input
 * 
 * <p>Record para representar os parâmetros de entrada para os casos de uso do domínio Role.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public record RoleInput(
		String name,
		String description
) {}
