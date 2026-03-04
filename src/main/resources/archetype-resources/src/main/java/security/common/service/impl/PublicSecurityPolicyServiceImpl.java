#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.service.impl;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import ${package}.security.common.dsl.SecurityPolicyDsl;
import ${package}.security.common.service.SecurityPolicyService;

/**
 * Classe: PublicSecurityPolicyServiceImpl <br>
 * Pacote: ${package}.security.common
 *
 * <p>Classe reponsável por implementar a política de segurança da aplicação específica para endpoins públicos. <br>
 * Implementa a interface {@link SecurityPolicyService}.</p>
 * 
 * <p><strong>ATENÇÃO: <br>
 * IMPLEMENTE A POLÍTICA DE SEGURANÇA PARA ENDPOINTS PÚBLICOS NESTA CLASSE.</strong></p>
 * 
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class PublicSecurityPolicyServiceImpl implements SecurityPolicyService {
	@Override
	public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
		// A política de segurança para os endpoints públicos é definida aqui
		SecurityPolicyDsl policy = new SecurityPolicyDsl(auth);
		
		// ============================================================================
        // 1. Endpoints definidos como públicos aqui
        // ============================================================================
		
        policy.get("/application/info").permitAll();

        // ============================================================================
        // 2.  Swagger, docs, etc.
        // ============================================================================
        
        policy.get("/swagger-ui.html").permitAll();
        policy.get("/swagger-ui/**").permitAll();
        policy.get("/v3/api-docs/**").permitAll();
        policy.get("/api/spring/v3/api-docs/**").permitAll();
	}
}
