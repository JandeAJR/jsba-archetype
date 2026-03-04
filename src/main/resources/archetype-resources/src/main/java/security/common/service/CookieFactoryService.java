#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.service;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface: CookieFactoryService <br>
 * Pacote: ${package}.application.core.service
 *
 * <p>Interface base para fornecer o contrato de fábrica para criação de cookies de acordo com o profile da aplicação. <br>
 * <strong>O profile pode ser: prod, develop, homolog e test.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface CookieFactoryService {
	ResponseCookie create(String cookieName, String cookieValue, Boolean isLogout);
	
	default String extractCookie(HttpServletRequest request, String cookieName) {
		if (cookieName == null || request.getCookies() == null) return null;
		
		return Arrays.stream(request.getCookies())
	            .filter(cookie -> cookieName.equals(cookie.getName()))
	            .findFirst()
	            .map(Cookie::getValue)
	            .orElse(null);
	}
	
	default BearerTokenResolver customTokenResolver(String cookieName) {
	    return request -> {
	        // Tenta buscar no Header "Authorization: Bearer ..." (Padrão para APIs públicas)
	        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	        
	        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
	            return authHeader.substring(7).trim();
	        }

	        // Se não achou no header, tenta buscar no Cookie (Padrão para domínios locais)
	        return extractCookie(request, cookieName);
	    };
	}
}
