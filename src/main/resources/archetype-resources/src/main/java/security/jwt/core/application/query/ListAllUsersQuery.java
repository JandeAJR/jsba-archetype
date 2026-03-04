#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ${package}.security.jwt.core.application.query.repository.UserQueryRepository;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import lombok.RequiredArgsConstructor;

/**
 * Classe: ListAllUsersQuery <br>
 * Pacote: ${package}.security.jwt.core.application.usecase.user
 * 
 * <p>Classe responável por implementar a query listar todos os usuários.</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class ListAllUsersQuery {
	private final UserQueryRepository queryRepository;
	
	public Page<UserOutput> list(Pageable pageable) {
		return queryRepository.selectAllUsers(pageable)
				.map(UserOutput::fromDomain);
	} 
}
