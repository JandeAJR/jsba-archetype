#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import ${package}.application.shared.exception.global.DatabaseException;
import ${package}.application.shared.exception.global.RefreshTokenException;
import ${package}.application.shared.exception.global.RefreshTokenNotFoundException;
import ${package}.application.shared.exception.global.SecurityConfigurationException;
import ${package}.application.shared.exception.global.SecurityStrategyNotDefinedException;
import ${package}.application.shared.exception.global.UserNotAuthenticated;
import ${package}.application.shared.util.Const;
import ${package}.application.shared.wrapper.ErrorResponseBuilder;
import ${package}.application.shared.wrapper.ResourceErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe: GlobalExceptionHandler<br>
 * Pacote: ${package}.application.core.exception
 *
 * <p>Classe responsável por tratar exceções de forma global na aplicação.<br>
 * É anotada com @RestControllerAdvice para interceptar exceções lançadas pelos controllers (resources).<br>
 * Os tratamentos para as classes globais de exception da aplicação devem ser implementados aqui</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	// Trata o erro de dados de requisição inválidos no body (JSON) 
	// ou parâmetros de consulta (query params) ou parâmetros de caminho (path variables)
	@ExceptionHandler(MethodArgumentNotValidException.class)  
    public ResponseEntity<ResourceErrorResponse> handleMethodArgumentNotValidException(
    		MethodArgumentNotValidException ex,
            HttpServletRequest request) {
		
		Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors()
	            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_ERROR,
                errors.entrySet()
	    			.stream()
					.map(entry -> String.format("Atributo %s: %s", entry.getKey(), entry.getValue()))
					.toList(),
				Const.BAD_REQUEST_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }	
	
	// Trata os erros de validação de dados usando as anotações de validação do Bean Validation (jakarta.validation)
	@ExceptionHandler(ConstraintViolationException.class) 
	public ResponseEntity<ResourceErrorResponse> handleConstraintViolationException(
	        ConstraintViolationException ex,
	        HttpServletRequest request) {

	    HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400

	    List<String> errors = ex.getConstraintViolations()
	            .stream()
	            .map(ConstraintViolation::getMessage)
	            .toList();
	    
	    ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_ERROR,
                errors,
                Const.BAD_REQUEST_VALIDATION_MESSAGE,
                request.getRequestURI());

	    log.error("ConstraintViolationException: {}", errors);
	    return ResponseEntity.status(httpStatus).body(errorResponse);
	}
	
	// Trata o erro de argumento inválido passado para um método (ex: IllegalArgumentException)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResourceErrorResponse> handleIllegalArgumentException(
	        IllegalArgumentException ex,
	        HttpServletRequest request) {

	    HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400

	    ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_ERROR,
                List.of(ex.getMessage()),
                Const.BAD_REQUEST_INVALID_ARGUMENT_MESSAGE,
                request.getRequestURI());

	    log.error(ex.getMessage());
	    return ResponseEntity.status(httpStatus).body(errorResponse);
	}
	
	// Trata o erro de tipo de parâmetro inválido (ex: passar uma string em um parâmetro que espera um número)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResourceErrorResponse> handleMethodArgumentTypeMismatchException(
	        MethodArgumentTypeMismatchException ex,
	        HttpServletRequest request) {

	    HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400

	    ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_INVALID_PARAMETERS_ERROR,
                List.of("Parâmetro '" + ex.getName() + "' com valor inválido: " + ex.getValue()),
                Const.BAD_REQUEST_INVALID_PARAMETERS_MESSAGE,
                request.getRequestURI());

	    log.error(ex.getMessage());
	    return ResponseEntity.status(httpStatus).body(errorResponse);
	}
		
	// Trata o erro de parâmetro obrigatório ausente na requisição (query param ou path variable)
	@ExceptionHandler(MissingServletRequestParameterException.class)  
    public ResponseEntity<ResourceErrorResponse> handleMissingServletRequestParameterException(
    		MissingServletRequestParameterException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_MISSING_PARAMETERS_ERROR + ex.getParameterName(),
                List.of(ex.getMessage()),
                Const.BAD_REQUEST_MISSING_PARAMETERS_MESSAGE,
                request.getRequestURI());

        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
	// Trata o erro de corpo da requisição malformado ou JSON inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)  
    public ResponseEntity<ResourceErrorResponse> handleHttpMessageNotReadableException(
    		HttpMessageNotReadableException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_JSON_PARSE_ERROR,
                List.of(ex.getMessage()),
                Const.BAD_REQUEST_JSON_PARSE_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de refresh token não localizado
    @ExceptionHandler(RefreshTokenNotFoundException.class)  
    public ResponseEntity<ResourceErrorResponse> handleRefreshTokenNotFoundException(
    		RefreshTokenNotFoundException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // http status 400
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.BAD_REQUEST_REFRESH_TOKEN_NOT_FOUND_ERROR,
                List.of(ex.getMessage()),
                Const.BAD_REQUEST_REFRESH_TOKEN_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de credenciais inválidas (Spring Security)
    @ExceptionHandler(BadCredentialsException.class) 
    public ResponseEntity<ResourceErrorResponse> handleBadCredentialsException(
    		BadCredentialsException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.INVALID_CREDENTIALS_ERROR,
                List.of(ex.getMessage()),
                Const.INVALID_CREDENTIALS_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro relacionado ao usuário não autenticado (exceção global personalizada da aplicação)
    @ExceptionHandler(UserNotAuthenticated.class) 
    public ResponseEntity<ResourceErrorResponse> handleUserNotAuthenticated(
    		UserNotAuthenticated ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.USER_NOT_AUTHENTICATED_ERROR,
                List.of(ex.getMessage()),
                Const.USER_NOT_AUTHENTICATED_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de autenticação genérico (Spring Security)
    @ExceptionHandler(AuthenticationException.class)  
    public ResponseEntity<ResourceErrorResponse> handleAuthenticationException(
    		AuthenticationException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.UNAUTHORIZED_ERROR,
                List.of(ex.getMessage()),
                Const.UNAUTHORIZED_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de RefreshTokenException (erros relacionados a refresh tokens)
    @ExceptionHandler(RefreshTokenException.class)  
    public ResponseEntity<ResourceErrorResponse> handleRefreshTokenException(
    		RefreshTokenException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.UNAUTHORIZED_REFRESH_TOKEN_ERROR,
                List.of(ex.getMessage()),
                Const.UNAUTHORIZED_REFRESH_TOKEN_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de SecurityException (erros relacionados a segurança em autenticações)
    @ExceptionHandler(SecurityException.class)  
    public ResponseEntity<ResourceErrorResponse> handleSecurityException(
    		SecurityException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.UNAUTHORIZED_SECURITY_ERROR,
                List.of(ex.getMessage()),
                Const.UNAUTHORIZED_SECURITY_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de acesso negado (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)  
    public ResponseEntity<ResourceErrorResponse> handleAccessDeniedException(
    		AccessDeniedException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.FORBIDDEN; // http status 403
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.FORBIDDEN_ERROR,
                List.of(ex.getMessage()),
                Const.FORBIDDEN_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de usuário não encontrado no contexto de segurança (Spring Security Context)
    @ExceptionHandler(UsernameNotFoundException.class)  
    public ResponseEntity<ResourceErrorResponse> handleUserNameNotFoundException(
            UsernameNotFoundException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // http status 404
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.USER_CONTEXT_NOT_FOUND_ERROR,
                List.of(ex.getMessage()),
                Const.USER_CONTEXT_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de recurso não encontrado (endpoint inexistente)
    @ExceptionHandler(NoHandlerFoundException.class)  
    public ResponseEntity<ResourceErrorResponse> handleNoHandlerFoundException(
    		NoHandlerFoundException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // http status 404
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.RESOURCE_NOT_FOUND,
                List.of(ex.getMessage()),
                Const.HANDLER_NOT_FOUND_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }    
        
    // Trata o erro de método HTTP não suportado para o endpoint (ex: POST em um endpoint que só aceita GET)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResourceErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED; // http status 405

        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.METHOD_NOT_ALLOWED_ERROR,
                List.of("Método(s) permitido(s): " + ex.getSupportedHttpMethods()),
                Const.METHOD_NOT_ALLOWED_MESSAGE,
                request.getRequestURI());

        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de exceção genérica de banco de dados (exceção global personalizada da aplicação)
    @ExceptionHandler(DatabaseException.class) 
    public ResponseEntity<ResourceErrorResponse> handleDataBaseException(
            DatabaseException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.CONFLICT; // http status 409
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.CONFLICT_DATABASE_ERROR,
                List.of(ex.getMessage()),
                Const.CONFLICT_DATABASE_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de violação de integridade de dados (exceção do Spring Data)
    // Por exemplo, ao tentar inserir um registro com uma chave única já existente ou ao violar uma restrição de chave estrangeira
    @ExceptionHandler(DataIntegrityViolationException.class)  
    public ResponseEntity<ResourceErrorResponse> handleDataIntegrityViolationException(
    		DataIntegrityViolationException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.CONFLICT; // http status 409
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.CONFLICT_DATA_INTEGRITY_VIOLATION_ERROR,
                List.of(ex.getMessage()),
                Const.CONFLICT_DATA_INTEGRITY_VIOLATION_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de tipo de mídia não suportado (Content-Type inválido)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)  
    public ResponseEntity<ResourceErrorResponse> handleHttpMediaTypeNotSupportedException(
    		HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE; // http status 415
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.UNSUPPORTED_MEDIA_TYPE_ERROR,
                List.of(ex.getMessage()),
                Const.UNSUPPORTED_MEDIA_TYPE_MESSAGE,
                request.getRequestURI());
        
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata o erro de configuração de segurança da aplicação (exceção global personalizada da aplicação)
    @ExceptionHandler(SecurityConfigurationException.class)  
    public ResponseEntity<ResourceErrorResponse> handleSecurityConfigurationException(
    		SecurityConfigurationException ex,
    		HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // http status 500
          
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.INTERNAL_SERVER_ERROR_SECURITY_CONFIGURATION_ERROR,
                List.of(ex.getMessage()),
                Const.INTERNAL_SERVER_ERROR_SECURITY_CONFIGURATION_MESSAGE,
                request.getRequestURI());
                
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    
    // Trata os erros relacionados à configuração da estratégia de autenticação da aplicação.
    @ExceptionHandler(SecurityStrategyNotDefinedException.class)  
    public ResponseEntity<ResourceErrorResponse> handleSecurityStrategyNotDefinedException(
    		SecurityStrategyNotDefinedException ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // http status 500
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.INTERNAL_SERVER_ERROR_AUTH_CONFIGURATION_ERROR,
                List.of(ex.getMessage()),
                Const.INTERNAL_SERVER_ERROR_AUTH_CONFIGURATION_MESSAGE,
                request.getRequestURI());
                
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
        
    // Trata os erros de validação de propriedades de configuração da aplicação.
    // Ex: application.properties ou application.yml
    @ExceptionHandler(BindValidationException.class)  
	public ResponseEntity<ResourceErrorResponse> handleBindValidationException(
	        BindValidationException ex,
	        HttpServletRequest request) {
	
	    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // http status 500
	
	    // 1. Pegar a lista tipada
	    List<ObjectError> allErrors = ex.getValidationErrors().getAllErrors();
	
	    // 2. Transformar em List<String>
	    List<String> errors = allErrors
	    		.stream()
	            .map(error -> {
	                // Se for erro de propriedade, pega o nome da propriedade
	                if (error instanceof FieldError fieldError) {
	                    return String.format("Propriedade '%s': %s",
	                            fieldError.getField(), fieldError.getDefaultMessage());
	                }
	                // Se não for de propriedade, usa o nome do objeto
	                return String.format("Objeto '%s': %s",
	                        error.getObjectName(), error.getDefaultMessage());
	            })
	            .toList();
	    
	    ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.INTERNAL_SERVER_ERROR_BIND_VALIDATION_ERROR,
                errors,
                Const.INTERNAL_SERVER_ERROR_BIND_VALIDATION_MESSAGE,
                request.getRequestURI());
	
	    log.error("BindValidationException: {}", errors);
	    return ResponseEntity.status(httpStatus).body(errorResponse);
	}
        
    // Trata qualquer outra exceção genérica não tratada pelos handlers específicos acima (catch-all)
    @ExceptionHandler(Exception.class)  
    public ResponseEntity<ResourceErrorResponse> handleException(
    		Exception ex,
            HttpServletRequest request) {
    	
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // http status 500
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.INTERNAL_SERVER_ERROR,
                List.of(ex.getMessage()),
                Const.INTERNAL_SERVER_ERROR_MESSAGE,
                request.getRequestURI());
                
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
