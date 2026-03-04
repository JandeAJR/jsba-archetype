#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception.global;

/**
 * Classe: SecurityStrategyNotDefinedException<br>
 * Pacote: ${package}.application.core.exception.global
 *
 * <p>Exceção personalizada para erros relacionados à configuração da estratégia de autenticação da aplicação.<br>
 * Estende {@link RuntimeException} para permitir tratamento específico em controladores.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public class SecurityStrategyNotDefinedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SecurityStrategyNotDefinedException(String message) {
		super(message);
	}
}
