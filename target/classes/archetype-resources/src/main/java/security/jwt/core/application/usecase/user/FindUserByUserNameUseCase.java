#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.usecase.user;

import org.springframework.stereotype.Service;

import ${package}.application.shared.exception.business.UserNotFoundException;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import ${package}.security.jwt.core.domain.model.User;
import ${package}.security.jwt.core.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: FindUserByUserNameUseCase<br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user
 * 
 * <p>Classe responável por implementar o caso de uso achar o usuário pelo username.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class FindUserByUserNameUseCase {
	private final UserRepository domainRepository;

	public UserOutput execute(String userName) {
		User user = domainRepository.findUserByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("Usuário: " + userName));
		
		return UserOutput.fromDomain(user);
	}
}
