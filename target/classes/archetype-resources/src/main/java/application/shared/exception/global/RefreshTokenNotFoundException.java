#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception.global;

/**
 * Classe: RefreshTokenNotFoundException<br>
 * Pacote: ${package}.application.core.exception.global
 *
 * <p>Exceção personalizada para erro relacionado ao refresh token não informado em uma requisição http.<br>
 * Estende {@link RuntimeException} para permitir tratamento específico em controladores.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class RefreshTokenNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RefreshTokenNotFoundException(String message) {
		super(message);
	}
}
