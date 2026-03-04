#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.user.input;

import java.util.List;

import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;

/**
 * Record: UserInput<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user.input
 * 
 * <p>Record para representar os parâmetros de entrada para os casos de uso do domínio User.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public record UserInput(
		String userName,
		String email,
		String name,
		String password,
		Boolean accountNonExpired,
		Boolean accountNonLocked,
		Boolean credentialsNonExpired,
		Boolean emailVerified,
		Boolean enabled,
		List<RoleOutput> roles
) {}
