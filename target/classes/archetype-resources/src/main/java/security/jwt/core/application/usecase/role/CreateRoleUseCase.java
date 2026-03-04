#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.role;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import ${package}.security.jwt.core.application.usecase.role.input.RoleInput;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.domain.repository.RoleRepository;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

/**
 * Classe: CreateRoleUseCase<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.role
 * 
 * <p>Classe responável por implementar o caso de uso Criar Role.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@Validated
@RequiredArgsConstructor
public class CreateRoleUseCase {
	private final RoleRepository domainRepository;
	
	public RoleOutput execute(@Valid RoleInput input) {
		Role role = new Role(
				null, // A geração do ID único é responsabilidade da camada de domínio
				input.name(), 
				input.description());
		
		domainRepository.create(role);
		return RoleOutput.fromDomain(role);
	}
}
