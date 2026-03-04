#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception.business;

/**
 * Classe: BusinessExceptionHandler<br>
 * Pacote: ${package}.application.core.exception.business
 *
 * <p>Exceção personalizada para indicar que um recurso não foi encontrado.<br>
 * Estende {@link RuntimeException} para permitir tratamento específico em controladores.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id) {
		super("ID: " + id.toString() + " não localizado");
	}
	
	public ResourceNotFoundException(String resource) {
		super(resource + " não localizado");
	}
}
