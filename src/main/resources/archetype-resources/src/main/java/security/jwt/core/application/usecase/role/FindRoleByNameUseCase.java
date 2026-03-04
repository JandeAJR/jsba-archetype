#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.role;

import org.springframework.stereotype.Service;

import ${package}.application.shared.exception.business.RoleNotFoundException;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: FindRoleByIdUseCase<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.role
 * 
 * <p>Classe responável por implementar o caso de uso achar a role pelo nome.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class FindRoleByNameUseCase {
	private final RoleRepository domainRepository;

	public RoleOutput execute(String name) {
		Role role = domainRepository.findRoleByName(name)
				.orElseThrow(() -> new RoleNotFoundException("Role: " + name));
		
		return RoleOutput.fromDomain(role);
	}
}
