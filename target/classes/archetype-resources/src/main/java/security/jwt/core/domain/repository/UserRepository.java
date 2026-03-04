#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.domain.repository;

import java.util.Optional;
import java.util.UUID;

import ${package}.security.jwt.core.domain.model.User;

/**
 * Interface: UserJpaRepository <br>
 * Pacote: ${package}.security.jwt.core.domain.repository
 *
 * <p>Interface de domínio para definir o contrato de CRUD para o repositório de usuários.</p>
 * 
 * <p><strong>É a interface de um repositório de domínio.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface UserRepository {
	User create(User model);
	Optional<User> findUserById(UUID id);
	Optional<User> findUserByUserName(String userName);
}
