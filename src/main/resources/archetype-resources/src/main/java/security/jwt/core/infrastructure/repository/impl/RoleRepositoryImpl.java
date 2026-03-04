#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ${package}.security.jwt.core.domain.model.Role;
import ${package}.security.jwt.core.domain.repository.RoleRepository;
import ${package}.security.jwt.core.infrastructure.entity.RoleEntity;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureRoleMapper;
import ${package}.security.jwt.core.infrastructure.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: RoleRepositoryImpl<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository.impl
 * 
 * <p>Classe responável por implementar os métodos de acesso ao repositório de dados 
 * definidos na interface de domínio {@link RoleRepository}.</p>
 * 
 * <p>É a implementação de um repositório de domínio.</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
	private final RoleJpaRepository jpaRepository;
	private final InfrastructureRoleMapper mapper;
	
	@Override
	public Role create(Role model) {
		RoleEntity entity = mapper.toEntityFromDomain(model);
		RoleEntity createdEntity = jpaRepository.save(entity);		
		return mapper.toDomainFromEntity(createdEntity);
	}

	@Override
	public Optional<Role> findRoleById(UUID id) {
		return jpaRepository.findById(id)          // Optional<RoleEntity>
				.map(mapper::toDomainFromEntity); // Mapeia para Optional<Role>
	}

	@Override
	public Optional<Role> findRoleByName(String name) {
		return jpaRepository.findByName(name)      // Optional<RoleEntity>
				.map(mapper::toDomainFromEntity); // Mapeia para Optional<Role>
	}	
}
