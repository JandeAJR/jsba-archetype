#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.projection;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Interface: ListUserRolesQuery <br>
 * Pacote: ${package}.security.jwt.core.infrastructure.projection
 *
 * <p>Interface de projeção (projection) que representa os dados da de querie específica.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface UserRolesQueryProjection {
	UUID getUserId();
	String getUserName();
	String getEmail();
	String getName();
	Boolean getEnabled();
	OffsetDateTime getDateCreated();
	String getRoleName();
	String getRoleDescription();	
}
