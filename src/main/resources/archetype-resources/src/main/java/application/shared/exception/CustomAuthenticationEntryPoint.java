#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.exception;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
 * Classe: CustomAuthenticationEntryPoint<br>
 * Pacote: ${package}.application.core.exception
 *
 * <p>Classe responsável por interceptar falhas de autenticação e retornar uma resposta personalizada em formato JSON, 
 * indicando que o token de autenticação está ausente ou inválido. Essa classe é utilizada para garantir que as respostas 
 * de erro sejam consistentes e informativas para os clientes que tentam acessar recursos protegidos sem fornecer credenciais válidas.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // http status 401
        
        ResourceErrorResponse errorResponse = ErrorResponseBuilder.build(
                httpStatus,
                Const.UNAUTHORIZED_ERROR,
                List.of(ex.getMessage()),
                Const.UNAUTHORIZED_MESSAGE,
                request.getRequestURI());
        
		log.error(ex.getMessage());
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
