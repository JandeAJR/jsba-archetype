#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception.business;

/**
 * Classe: RoleNotFoundException<br>
 * Pacote: ${package}.application.core.exception.business
 *
 * <p>Exceção personalizada para indicar que uma role não foi encontrada.<br>
 * Estende {@link RuntimeException} para permitir tratamento específico em controladores.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class RoleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RoleNotFoundException(String message) {
		super(message);
	}
}
