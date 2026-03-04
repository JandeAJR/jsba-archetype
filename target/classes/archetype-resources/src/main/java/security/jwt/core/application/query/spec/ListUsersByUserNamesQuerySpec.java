#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ${package}.application.shared.persistence.specification.jpa.QuerySpecification;
import ${package}.security.jwt.core.application.query.filter.UserFilter;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureUserMapper;
import ${package}.security.jwt.core.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: ListUsersByUserNamesQuerySpec <br>
 * Pacote: ${package}.security.jwt.core.application.query.spec
 * 
 * <p>Classe responável por implementar a query listar usuário por uma lista de nomes de usuários, 
 * fazendo uso da classe {@link QuerySpecification} para montar os filtros de consulta.</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class ListUsersByUserNamesQuerySpec {
	private final UserJpaRepository jpaRepository;
	private final InfrastructureUserMapper mapper;
	
	public Page<UserOutput> list(UserFilter filter, Pageable pageable) {
		Specification<UserEntity> spec = Specification
				.where(QuerySpecification.<UserEntity, String>criteriaByIn(
						"userName",            // Nome exato do atributo na Entity JPA
						filter.usernames()));  // Filtro informado na query string da url da requisição http
		
		return jpaRepository.findAll(spec, pageable)
				.map(mapper::toDomainFromEntity)
				.map(UserOutput::fromDomain);
	} 
}
