#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.wrapper;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

import ${package}.application.shared.util.Const;

/**
 * Classe: ErrorResponseBuilder <br>
 * Pacote: ${package}.application.core.wrapper
 *
 * <p>Classe utilitária responsável por construir objetos do tipo ResourceErrorResponse, 
 * que encapsulam informações detalhadas sobre erros ocorridos durante o processamento de requisições. 
 * Essa classe é utilizada para criar respostas de erro consistentes e informativas, 
 * contendo o status HTTP, mensagem de erro, detalhes adicionais e o caminho da requisição que causou o erro.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class ErrorResponseBuilder {
	private ErrorResponseBuilder() {
		throw new UnsupportedOperationException(Const.WRAPPER_CLASS_CANNOT_BE_INSTANTIATED);
	}
	
	public static ResourceErrorResponse build(
	        HttpStatus status,
	        String message,
	        List<String> errors,
	        String detail,
	        String path) {

	    return new ResourceErrorResponse(
	            status.value(),
	            status.getReasonPhrase(),
	            message,
	            errors,
	            detail,
	            path,
	            Instant.now());
	}
}
