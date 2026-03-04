#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import ${package}.security.common.service.HashingService;
import ${package}.security.jwt.core.application.mapper.ApplicationRoleMapper;
import ${package}.security.jwt.core.application.usecase.user.input.UserInput;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.domain.model.User;
import ${package}.security.jwt.core.domain.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Classe: CreateUserUseCase<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user
 * 
 * <p>Classe responável por implementar o caso de uso Criar Usuário.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@Validated
@RequiredArgsConstructor
public class CreateUserUseCase {
	private final UserRepository domainRepository;
	private final HashingService encoder;
	private final ApplicationRoleMapper applicationRoleMapper;
	
	public UserOutput execute(@Valid UserInput input) {
		// Gera o hash da senha.
		String password = encoder.hash(input.password());
		
		// Mapeia a lista de RoleOutput (vinda da camada de application service) 
		// para uma lista de Role (domínio) usando o mapper.
		List<Role> roles = applicationRoleMapper.toDomainFromApplicationOutputList(input.roles());
				
		User user = new User(
				null, // A geração do Id único é responsabilidade da camada de domínio.
				input.userName(),
				input.email(),
				input.name(),
				password,
				input.accountNonExpired(),
				input.accountNonLocked(),
				input.credentialsNonExpired(),
				input.emailVerified(),
				input.enabled(),
				null, // A geração da data de criação do usuário é feita na camada e domínio.
				roles);
		
		domainRepository.create(user);
		return UserOutput.fromDomain(user);
	}
}
