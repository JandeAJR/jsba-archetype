#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ${package}.security.jwt.core.domain.model.User;
import ${package}.security.jwt.core.domain.repository.UserRepository;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureUserMapper;
import ${package}.security.jwt.core.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserRepositoryImpl<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository.impl
 * 
 * <p>Classe responável por implementar os métodos de acesso ao repositório de dados 
 * definidos na interface de domínio {@link UserRepository}.</p>
 * 
 * <p><strong>É a implementação de um repositório de domínio.</strong></p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
	private final UserJpaRepository jpaRepository;
	private final InfrastructureUserMapper mapper;
	
	@Override
	public User create(User model) {
		UserEntity entity = mapper.toEntityFromDomain(model);
		UserEntity createdEntity = jpaRepository.save(entity);
		return mapper.toDomainFromEntity(createdEntity);
	}

	@Override
	public Optional<User> findUserById(UUID id) {
		return jpaRepository.findById(id)          // Optional<RoleEntity>
				.map(mapper::toDomainFromEntity); // Mapeia para Optional<Role>
	}

	@Override
	public Optional<User> findUserByUserName(String userName) {
		return jpaRepository.findByUserName(userName)  // Optional<RoleEntity>
				.map(mapper::toDomainFromEntity);     // Mapeia para Optional<Role>
	}
}
