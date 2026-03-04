#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.infrastructure.service.impl;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import ${package}.application.shared.util.Const;
import ${package}.security.common.dsl.SecurityPolicyDsl;
import ${package}.security.common.service.SecurityPolicyService;

/**
 * Classe: KeycloakSecurityPolicyServiceImpl <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.service
 *
 * <p>Classe reponsável por implementar a política de segurança da aplicação. <br>
 * Implementa a interface {@link SecurityPolicyService}.</p>
 * 
 * <p><strong>ATENÇÃO:</strong><br>
 * <strong>CADA MÓDULO DA APLICAÇÃO DEVE TER UMA CLASSE RESPONSÁVEL POR APLICAR A POLÍTICA DE SEGURANÇA.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class KeycloakSecurityPolicyServiceImpl implements SecurityPolicyService {
	@Override
	public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
		// A política de segurança para os domínios da aplicação é aplicada aqui
		SecurityPolicyDsl policy = new SecurityPolicyDsl(auth);
			
		// ============================================================================
        // 1. Usando Keycloak
        // ============================================================================
		
        policy.get("/keycloak/test/users").roles(Const.ROLE_ADMIN);
        policy.get("/keycloak/test/users/admin").roles(Const.ROLE_ADMIN);
        policy.get("/keycloak/test/users/basic").roles(Const.ROLE_BASIC_USER);
        policy.get("/keycloak/test/users/anonymous").permitAll();
	}
}
