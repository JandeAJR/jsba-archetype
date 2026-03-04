#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ${package}.security.jwt.core.application.query.output.UserRolesQueryOutput;
import ${package}.security.jwt.core.application.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: ListUserRolesQuery <br>
 * Pacote: ${package}.security.jwt.core.application.query
 * 
 * <p>Classe responável por implementar o caso de uso de consulta Usuário e suas Roles (UserRoles).</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class ListUserRolesQuery {
	private final UserQueryRepository queryRepository;
	
	public Page<UserRolesQueryOutput> listWithJpqlQuery(String userName, Pageable pageable) {
		return queryRepository.selectUserRolesWithJpqlQuery(userName, pageable);
	}
	
	public Page<UserRolesQueryOutput> listWithNativeQuery(String userName, Boolean exactly, Pageable pageable) {
		String exactlyQueryOperator = Boolean.TRUE.equals((exactly)) ? "" : "%";
		return queryRepository.selectUserRolesWithNativeQuery(userName + exactlyQueryOperator, pageable);
	}
}
