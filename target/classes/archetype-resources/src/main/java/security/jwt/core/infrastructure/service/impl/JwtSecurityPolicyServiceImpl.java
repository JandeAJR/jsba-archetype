#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import ${package}.application.shared.util.Const;
import ${package}.security.common.dsl.SecurityPolicyDsl;
import ${package}.security.common.service.SecurityPolicyService;

/**
 * Classe: JwtSecurityPolicyServiceImpl <br>
 * Pacote: ${package}.security.jwt.core.service
 *
 * <p>Classe reponsável por implementar a política de segurança da aplicação. <br>
 * Implementa a interface {@link SecurityPolicyService}.</p>
 * 
 * <p>Módulo: Jwt <br>
 * A autenticação é feita internamente pela própria aplicação, usando token JWT.</p>
 * 
 * <p><strong>ATENÇÃO: <br>
 * CADA MÓDULO DA APLICAÇÃO DEVE TER UMA CLASSE RESPONSÁVEL POR APLICAR A POLÍTICA DE SEGURANÇA.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class JwtSecurityPolicyServiceImpl implements SecurityPolicyService {
	@Override
	public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {	
		// A política de segurança para os domínios da aplicação é aplicada aqui		
		SecurityPolicyDsl policy = new SecurityPolicyDsl(auth);

        // ============================================================================
        // 1. Segurança do domínio de autenticação JWT
        // ============================================================================
		
        policy.regexPost("^/auth/login${symbol_dollar}").permitAll();
        policy.regexPost("^/auth/refresh${symbol_dollar}").permitAll();
        policy.regexPost("^/auth/logout${symbol_dollar}").permitAll();

        // ============================================================================
        // 2. Segurança do domínio Role
        // ============================================================================

        // POST /roles  => apenas ADMIN
        policy.post("/roles").roles(Const.ROLE_ADMIN);

        // GET /roles, /roles/all, /roles/{id}, /roles/name/{name} => BASIC_USER
        policy.get("/roles").roles(Const.ROLE_BASIC_USER);
        policy.get("/roles/all").roles(Const.ROLE_BASIC_USER);
        policy.regexGet("^/roles/[a-fA-F0-9${symbol_escape}${symbol_escape}-]{36}${symbol_dollar}").roles(Const.ROLE_BASIC_USER);    // id UUID
        policy.regexGet("^/roles/name/[A-Za-z0-9_${symbol_escape}${symbol_escape}-]+${symbol_dollar}").roles(Const.ROLE_BASIC_USER); // nome seguro

        // ============================================================================
        // 3. Segurança do domínio User
        // ============================================================================
        
        // POST /users  => apenas ADMIN
        policy.post("/users").roles(Const.ROLE_ADMIN);

        // GET /users, /users/all, /users/{id}, /users/username/{username} => BASIC_USER
        policy.get("/users").roles(Const.ROLE_BASIC_USER);
        policy.get("/users/all").roles(Const.ROLE_BASIC_USER);
        policy.regexGet("^/users/[a-fA-F0-9${symbol_escape}${symbol_escape}-]{36}${symbol_dollar}").roles(Const.ROLE_BASIC_USER);         // id UUID
        policy.regexGet("^/users/username/[A-Za-z0-9._${symbol_escape}${symbol_escape}-]+${symbol_dollar}").roles(Const.ROLE_BASIC_USER); // username seguro

        // ============================================================================
        // 4. Endpoints de relatórios internos (Jwt)
        // ============================================================================
        
        policy.post("/jwt/reports/**").roles(Const.ROLE_ADMIN);
	}
}
