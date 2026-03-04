#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.wrapper;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: ResourceErrorResponse<br>
 * Pacote: ${package}.application.core.wrapper
 *
 * <p>Classe reponsável por encapsular (wrapper) e padronizar a estrutura de erros nas respostas da API.<br>
 * Utilizada nas classes GlobalExceptionHandler e BusinessExceptionHandler para retornar detalhes sobre erros ocorridos.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
public class ResourceErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer status;
    private String description;
    private String error;
    private List<String> errors;
    private String message;
    private String path;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") // ISO 8601
    private Instant timestamp;

    public ResourceErrorResponse(
    		Integer status, 
    		String description,
    		String error, 
    		List<String> errors,
    		String message, 
    		String path, 
    		Instant timestamp) {
    	
    	this.status = status;
    	this.description = description;
    	this.error = error;
    	this.errors = errors;
    	this.message = message;
    	this.path = path;
        this.timestamp = timestamp;
    }	
}
