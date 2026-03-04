#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.service.impl;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import ${package}.application.shared.exception.global.UserNotAuthenticated;
import ${package}.application.shared.util.Const;
import ${package}.security.common.domain.model.AuthenticatedUser;
import ${package}.security.common.service.AuthenticatedUserService;

/**
 * Classe: AuthenticatedUserServiceImpl <br>
 * Pacote: ${package}.security.common.service.impl
 *
 * <p>Classe de implementação do serviço que irá retornar os dados do usuário autenticado. 
 * Esta classe é responsável por fornecer as informações do usuário atualmente autenticado 
 * no contexto de segurança da aplicação. <br>
 * Implementa a interface {@link AuthenticatedUserService}.</p>
 *
 * <p>Esta classe segue o padrão <strong>OpenID Connect (OIDC)</strong> para a nomenclatura das clains a serem
 * extraídas dos tokens de acesso. <br>
 * Exemplos: <br>
 * - preferred_username <br>
 * - name <br>
 * - email <br>
 * </p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
    private Jwt getJwt() {
    	// Obtém o objeto de autenticação do contexto de segurança do Spring Security.
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new UserNotAuthenticated(Const.USER_CONTEXT_NOT_FOUND_ERROR);
        }

        return jwtAuth.getToken();
    }
    
    private List<String> extractRolesFromAuthorities() {
    	// Obtém o objeto de autenticação do contexto de segurança do Spring Security.
        var auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
			throw new UserNotAuthenticated(Const.USER_CONTEXT_NOT_FOUND_ERROR);
		}

        return auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
    }
	
	@Override
	public AuthenticatedUser getCurrentUser() {
		// Este método retorna os dados do usuário autenticado.
		Jwt jwt = getJwt();
		List<String> roles = extractRolesFromAuthorities();
		
		AuthenticatedUser user = new AuthenticatedUser();
		user.setId(jwt.getSubject());
		user.setUserName(jwt.getClaim("preferred_username"));
		user.setEmail(jwt.getClaim("email"));
		user.setName(jwt.getClaim("name"));
		user.setEmailVerified(jwt.getClaim("email_verified"));
		user.setEnabled(true);
		user.setDateCreated(null);
		user.setRoles(roles);

        return user;
	}
	
	@Override
	public String extractUserId() {
		return getJwt().getSubject();
	}

	@Override
	public String extractUsername() {
		return getJwt().getClaim("preferred_username");
	}

	@Override
	public String extractEmail() {
		return getJwt().getClaim("email");
	}

	@Override
	public String extractName() {
		return getJwt().getClaim("name");
	}

	@Override
	public List<String> extractRoles() {
		return extractRolesFromAuthorities();
	}
}
