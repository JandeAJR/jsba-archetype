#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ${package}.application.shared.util.Const;
import ${package}.application.shared.wrapper.ErrorResponseBuilder;
import ${package}.application.shared.wrapper.ResourceErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe: CustomAccessDeniedHandler<br>
 * Pacote: ${package}.application.core.exception
 *
 * <p>Classe responsável por interceptar falhas de autorização e retornar uma resposta personalizada em formato JSON, 
 * indicando que o token de autenticação está ausente ou inválido. Essa classe é utilizada para garantir que as respostas 
 * de erro sejam consistentes e informativas para os clientes que tentam acessar recursos protegidos sem fornecer roles válidas.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;    

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        HttpStatus httpStatus = HttpStatus.FORBIDDEN; // http status 403
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.FORBIDDEN_ERROR,
                List.of(ex.getMessage()),
                Const.FORBIDDEN_MESSAGE,
                request.getRequestURI());

        log.error(ex.getMessage());  
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
