#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ${package}.security.jwt.core.application.query.repository.RoleQueryRepository;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import lombok.RequiredArgsConstructor;

/**
 * Classe: ListAllRolesQuery<br>
 * Pacote: ${package}.security.jwt.core.application.query
 * 
 * <p>Classe responável por implementar a query listar todas as roles.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class ListAllRolesQuery {
	private final RoleQueryRepository queryRepository;
	
	public Page<RoleOutput> list(Pageable pageable) {
		return queryRepository.selectAllRoles(pageable)
				.map(RoleOutput::fromDomain);
	}
}
