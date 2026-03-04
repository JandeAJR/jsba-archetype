#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.output;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Record: UserRolesQueryOutput <br>
 * Pacote: ${package}.security.jwt.core.application.query.output
 * 
 * <p>Record para representar os parâmetros de saída para os casos de uso de consultas (UseCase Queries).</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public record UserRolesQueryOutput(
	UUID userId,
	String userName,
	String email,
	String name,
	Boolean enabled,
	OffsetDateTime dateCreated,
	String roleName,
	String roleDescription
) {}
