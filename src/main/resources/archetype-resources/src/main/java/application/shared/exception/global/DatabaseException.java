#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception.global;

/**
 * Classe: DatabaseException<br>
 * Pacote: ${package}.application.core.exception.global
 *
 * <p>Exceção personalizada para erros relacionados ao banco de dados.<br>
 * Estende {@link RuntimeException} para permitir tratamento específico em controladores.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String message) {
		super(message);
	}
}
