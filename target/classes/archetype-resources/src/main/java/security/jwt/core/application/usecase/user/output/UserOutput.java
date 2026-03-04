#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.user.output;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.domain.model.User;

/**
 * Record: UserOutput<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user.output
 * 
 * <p>Record para representar os parâmetros de saída para os casos de uso do domínio User.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public record UserOutput(
		UUID id,
		String userName,
		String email,
		String name,
		Boolean accountNonExpired,
		Boolean accountNonLocked,
		Boolean credentialsNonExpired,
		Boolean emailVerified,
		Boolean enabled,
		OffsetDateTime dateCreated,
		List<Role> roles
) {
	public static UserOutput fromDomain(User user) {
		return new UserOutput(
				user.getId(), 
				user.getUserName(), 
				user.getEmail(),
				user.getName(),
				user.getAccountNonExpired(), 
				user.getAccountNonLocked(), 
				user.getCredentialsNonExpired(), 
				user.getEmailVerified(),
				user.getEnabled(), 
				user.getDateCreated(),
				user.getRoles());
	}
}
