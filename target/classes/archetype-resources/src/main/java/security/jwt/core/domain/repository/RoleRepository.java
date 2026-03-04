#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.domain.repository;

import java.util.Optional;
import java.util.UUID;

import ${package}.security.jwt.core.domain.model.Role;

/**
 * Interface: RoleJpaRepository <br>
 * Pacote: ${package}.security.jwt.core.domain.repository
 *
 * <p>Interface de domínio para definir o contrato de CRUD para o repositório de roles de usuários.</p>
 * 
 * <p><strong>É a interface de um repositório de domínio.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface RoleRepository {
	Role create(Role model);
	Optional<Role> findRoleById(UUID id);
	Optional<Role> findRoleByName(String name);
}
