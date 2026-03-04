#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ${package}.security.jwt.core.infrastructure.entity.RoleEntity;

/**
 * Interface: RoleJpaRepository<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository
 * 
 * <p>Interface JpaRepository reponsável por definir as operações de acesso a dados para a entidade RoleEntity.<br>
 * Extende as interfaces {@link JpaRepository} e {@link JpaSpecificationExecutor} do Spring Framework Data Jpa.</p>
 * 
 * <p>É a interface de um repositório Jpa</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public interface RoleJpaRepository 
		extends JpaRepository<RoleEntity, UUID>, JpaSpecificationExecutor<RoleEntity> {
	
	// O atributo name precisa existir na entidade RoleEntity -> findBy{atributo}
	public Optional<RoleEntity> findByName(String name); 
}
