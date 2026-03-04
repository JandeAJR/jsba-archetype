#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ${package}.application.shared.persistence.specification.jpa.QuerySpecification;
import ${package}.security.jwt.core.application.query.filter.RoleFilter;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.infrastructure.entity.RoleEntity;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureRoleMapper;
import ${package}.security.jwt.core.infrastructure.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: ListRolesByNamesQuerySpec <br>
 * Pacote: ${package}.security.jwt.core.application.query.spec
 * 
 * <p>Classe responável por implementar a query listar roles por uma lista de nomes de roles, 
 * fazendo uso da classe {@link QuerySpecification} para montar os filtros de consulta.</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class ListRolesByNamesQuerySpec {
	private final RoleJpaRepository jpaRepository;
	private final InfrastructureRoleMapper mapper;
	
	public Page<RoleOutput> list(RoleFilter filter, Pageable pageable) {
		Specification<RoleEntity> spec = Specification
				.where(QuerySpecification.<RoleEntity, String>criteriaByIn(
						"name",            // Nome exato do atributo na Entity JPA
						filter.names()));  // Filtro informado na query string da url da requisição http
		
		return jpaRepository.findAll(spec, pageable)
				.map(mapper::toDomainFromEntity)
				.map(RoleOutput::fromDomain);
	} 
}
