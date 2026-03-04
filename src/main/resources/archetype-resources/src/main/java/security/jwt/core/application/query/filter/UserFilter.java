#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.query.filter;

import java.util.List;

import ${package}.application.shared.query.filter.OperatorFilter;

/**
 * Record: UserFilter <br>
 * Pacote: ${package}.security.jwt.core.application.query.filter
 * 
 * <p>Record para representar os filtros para consultar (query) usuários pelo nome.</p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public record UserFilter(
	String name,
	List<String> usernames,
	OperatorFilter operator
) {}
