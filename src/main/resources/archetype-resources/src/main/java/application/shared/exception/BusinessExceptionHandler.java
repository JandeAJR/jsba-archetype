#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ${package}.application.shared.exception.business.ResourceNotFoundException;
import ${package}.application.shared.exception.business.RoleNotFoundException;
import ${package}.application.shared.exception.business.UserNotFoundException;
import ${package}.application.shared.util.BusinessConst;
import ${package}.application.shared.wrapper.ErrorResponseBuilder;
import ${package}.application.shared.wrapper.ResourceErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe: BusinessExceptionHandler<br>
 * Pacote: ${package}.application.core.exception
 *
 * <p>Classe para tratar exceções de regras de negócio na aplicação.<br>
 * É anotada com @RestControllerAdvice para interceptar exceções lançadas pelos controllers (resources).<br>
 * Os tratamentos para as classes de business exceptions da aplicação devem ser implementados aqui</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {
	// Trata a exceção ResourceNotFoundException, que é lançada quando um recurso da aplicação não é encontrado.
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceErrorResponse> resourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
		
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // http status 404
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                BusinessConst.RESOURCE_NOT_FOUND_ERROR,
                List.of(ex.getMessage()),
                BusinessConst.RESOURCE_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());  
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
	    
    // Trata o erro de role não encontrada
    @ExceptionHandler(RoleNotFoundException.class)  
    public ResponseEntity<ResourceErrorResponse> handleRoleNotFoundException(
    		RoleNotFoundException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // http status 404
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                BusinessConst.ROLE_NOT_FOUND,
                List.of(ex.getMessage()),
                BusinessConst.ROLE_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de usuário não encontrado
    @ExceptionHandler(UserNotFoundException.class)  
    public ResponseEntity<ResourceErrorResponse> handleUserNotFoundException(
    		UserNotFoundException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // http status 404
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                BusinessConst.USER_NOT_FOUND,
                List.of(ex.getMessage()),
                BusinessConst.USER_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
