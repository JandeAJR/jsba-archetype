#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ${package}.application.shared.exception.business.UserNotFoundException;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import ${package}.security.jwt.core.domain.model.User;
import ${package}.security.jwt.core.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: FindUserByIdUseCase<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user
 * 
 * <p>Classe responável por implementar o caso de uso achar o usuário pelo id.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {
	private final UserRepository domainRepository;
	
	public UserOutput execute(String id) {
		UUID uuid = UUID.fromString(id); // É preciso converter o objeto UUID para String
		
		User user = domainRepository.findUserById(uuid)
				.orElseThrow(() -> new UserNotFoundException("Usuário id: " + id));
		
		return UserOutput.fromDomain(user);
	}
}
