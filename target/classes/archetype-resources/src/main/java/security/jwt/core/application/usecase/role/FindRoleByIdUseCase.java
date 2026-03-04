#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.role;

import java.util.UUID;

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
 * <p>Classe responável por implementar o caso de uso achar a role pelo id.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class FindRoleByIdUseCase {
	private final RoleRepository domainRepository;
	
	public RoleOutput execute(String id) {
		UUID uuid = UUID.fromString(id); // É preciso converter o objeto UUID para String
		
		Role role = domainRepository.findRoleById(uuid)
				.orElseThrow(() -> new RoleNotFoundException("Role id: " + id));
		
		return RoleOutput.fromDomain(role);
	}
}
