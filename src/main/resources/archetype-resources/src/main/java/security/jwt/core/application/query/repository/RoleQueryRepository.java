#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ${package}.security.jwt.core.domain.model.Role;

/**
 * Interface: RoleQueryRepository <br>
 * Pacote: ${package}.security.jwt.core.application.query.repository
 *
 * <p>Interface de repositório de consultas. <br>
 * É responsável por expor os métodos de consulta que serão utilizados pelo módulo da aplicação.</p>
 * 
 * <p><strong>É a interface de um repositório de consulta (camada de aplicação).</strong></p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public interface RoleQueryRepository {
	Page<Role> selectAllRoles(Pageable pageable);
}
