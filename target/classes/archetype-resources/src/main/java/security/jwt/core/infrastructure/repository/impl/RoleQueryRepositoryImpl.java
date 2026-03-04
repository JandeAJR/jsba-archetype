#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ${package}.security.jwt.core.application.query.repository.RoleQueryRepository;
import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureRoleMapper;
import ${package}.security.jwt.core.infrastructure.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: RoleQueryRepositoryImpl <br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository.impl
 * 
 * <p>Classe responável por implementar os métodos de acesso ao repositório de dados 
 * definidos na interface do repositório de consultas {@link RoleQueryRepository}.</p>
 * 
 * <p><strong>É a implementação de um repositório de consultas (que fica na camada de aplicação).</strong></p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Repository
@RequiredArgsConstructor
public class RoleQueryRepositoryImpl implements RoleQueryRepository {
	private final RoleJpaRepository jpaRepository;
	private final InfrastructureRoleMapper mapper;
	
	@Override
	public Page<Role> selectAllRoles(Pageable pageable) {
		return jpaRepository.findAll(pageable)     // Page<RoleEntity>
				.map(mapper::toDomainFromEntity); // Mapeia cada RoleEntity para Role
	}
}
