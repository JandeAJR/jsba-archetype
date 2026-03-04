#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.filter;

import java.util.List;

/**
 * Record: RoleFilter <br>
 * Pacote: ${package}.security.jwt.core.application.query.filter
 * 
 * <p>Record para representar os filtros para consultar (query) roles pelo nome.</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public record RoleFilter(
	List<String> names
) {}
