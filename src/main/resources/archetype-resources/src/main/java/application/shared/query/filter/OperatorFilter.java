#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.query.filter;

/**
 * Enum: OperatorFilter <br>
 * Pacote: ${package}.application.shared.persistence.specification.jpa
 *
 * <p>Enum com os operadores válidos para consultas de dados.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public enum OperatorFilter {
	// Para Strings e tipos gerais
    EQUALS,       // WHERE A = A
    NOT_EQUALS,   // WHERE A != A
    STARTS_WITH,  // WHERE A LIKE A%
    CONTAINS,     // WHERE A LIKE %A%  
    ENDS_WITH,    // WHERE A LIKE %A
    
    // Para Números e Datas (Comparáveis)
    GREATER_THAN,        // WHERE A > A
    LESS_THAN,           // WHERE A < A
    GREATER_THAN_EQUAL,  // WHERE A >= A
    LESS_THAN_EQUAL,     // WHERE A <= A
    
    // Mútiplos valores
    IN  // WHERE A IN (A, B, C, ...)
}
