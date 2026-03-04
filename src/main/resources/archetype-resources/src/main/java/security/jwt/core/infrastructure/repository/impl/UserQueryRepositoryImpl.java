#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ${package}.security.jwt.core.application.query.output.UserRolesQueryOutput;
import ${package}.security.jwt.core.application.query.repository.UserQueryRepository;
import ${package}.security.jwt.core.domain.model.User;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureUserMapper;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureUserRolesQueryMapper;
import ${package}.security.jwt.core.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtQueryRepositoryImpl <br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository.impl
 * 
 * <p>Classe responável por implementar os métodos de acesso ao repositório de dados 
 * definidos na interface do repositório de consultas {@link UserQueryRepository}.</p>
 * 
 * <p><strong>É a implementação de um repositório de consultas (que fica na camada de aplicação).</strong></p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
	private final UserJpaRepository jpaRepository;
	private final InfrastructureUserMapper userMapper;
	private final InfrastructureUserRolesQueryMapper userRolesQueryMapper;

	@Override
	public Page<User> selectAllUsers(Pageable pageable) {
		return jpaRepository.findAll(pageable) // Page<RoleEntity>
				.map(userMapper::toDomainFromEntity); // Mapeia cada RoleEntity para Role
	}

	@Override
	public Page<UserRolesQueryOutput> selectUserRolesWithJpqlQuery(String userName, Pageable pageable) {
		return jpaRepository.selectUserRolesWithJpqlQuery(userName, pageable)   // Page<ListUserRolesQuery> (Projection)
				.map(userRolesQueryMapper::toApplicationOutputFromProjection); // Mapeia cada ListUserRolesQuery para UserRolesQueryOutput
	}
	
	@Override
	public Page<UserRolesQueryOutput> selectUserRolesWithNativeQuery(String userName, Pageable pageable) {
		return jpaRepository.selectUserRolesWithNativeQuery(userName, pageable) // Page<ListUserRolesQuery> (Projection)
				.map(userRolesQueryMapper::toApplicationOutputFromProjection); // Mapeia cada ListUserRolesQuery para UserRolesQueryOutput
	}
}
