#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.api.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.application.shared.exception.global.RefreshTokenNotFoundException;
import ${package}.application.shared.util.Const;
import ${package}.security.common.domain.model.AuthenticatedUser;
import ${package}.security.common.service.AuthenticatedUserService;
import ${package}.security.oauth2.keycloak.config.properties.KeycloakProperties;
import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakAuthenticatedUserResponse;
import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakTokensResponse;
import ${package}.security.oauth2.keycloak.core.api.mapper.ApiAuthenticatedUserMapper;
import ${package}.security.oauth2.keycloak.service.KeycloakAuthService;
import ${package}.security.oauth2.keycloak.service.KeycloakCookieFactoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Classe: KeycloakAuthResource <br>
 * Pacote: ${package}.security.oauth2.keycloak.modules.oauth2.api.rest
 *
 * <p>Recurso (controller) REST para fornecer os endpoints de autenticação no Keycloak.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KeycloakAuthResource {
	private final KeycloakAuthService authService;
	private final KeycloakCookieFactoryService cookieFactoryService;
	private final KeycloakProperties keycloakProperties;
	private final AuthenticatedUserService authenticatedUserService;
	private final ApiAuthenticatedUserMapper authenticatedUserMapper;
	
	@Value("${symbol_dollar}{frontend-home-page:" + Const.FRONTEND_HOME_PAGE_URL + "}")
	private String frontendHomePage;
	
	@Value("${symbol_dollar}{frontend-login-page:" + Const.FRONTEND_LOGIN_PAGE_URL + "}")
	private String frontendLoginPage;
	
	// Tag para o Swagger UI
	private static final String TAG = "Keycloak";
		
	@GetMapping("/callback")
    @Operation(
    	summary = "Callback entre o backend e o Keycloak", 
    	description = "Endpoint para realzar Callback entre o backend e o Keycloak e trocar o código de acesso por um token JWT válido.",
    	tags = {TAG}
    )
	public ResponseEntity<Void> callback(@RequestParam String code, HttpServletResponse response) {      
	    // Troca o CODE pelo TOKEN no Keycloak
	    KeycloakTokensResponse tokens = authService.exchangeCodeForToken(code);

	    // Cookie para o Access Token no header da resposta (expiração curta).
	    ResponseCookie accessCookie = cookieFactoryService
	    		.create(Const.ACCESS_TOKEN_COOKIE_NAME, tokens.getAccessToken(), false); // isLogout = false
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
	    
	    // Cookie para o Refresh Token no header da resposta (expiração longa).
	    ResponseCookie refreshCookie = cookieFactoryService
	    		.create(Const.REFRESH_TOKEN_COOKIE_NAME, tokens.getRefreshToken(), false); // isLogout = false
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

	    // Redireciona o usuário para o Frontend.
	    return ResponseEntity.status(HttpStatus.FOUND)
	            .location(URI.create(frontendHomePage))
	            .build();
	}
	
	@PostMapping("/refresh")
    @Operation(
    	summary = "Solicitar um refresh token", 
    	description = "Endpoint para realzar a solicitação de um refresh token para o Keycloak.",
    	tags = {TAG}
    )
	public ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
	    // Extrai o Refresh Token do Cookie de forma segura.
	    String refreshToken = cookieFactoryService.extractCookie(request, Const.REFRESH_TOKEN_COOKIE_NAME);

	    // Verfica se o refresh token foi informado no cookie de usuário.
	    if (refreshToken == null) {
	        throw new RefreshTokenNotFoundException(Const.REFRESH_TOKEN_COOKIE_VALUE_NOT_FOUND);
	    }

	    // Chama o Keycloak para renovar os tokens
	    KeycloakTokensResponse newTokens = authService.refreshToken(refreshToken);
	    
	    // Atualiza o valor do cookie para o Access Token no header da resposta (expiração curta).
	    ResponseCookie accessCookie = cookieFactoryService
	    		.create(Const.ACCESS_TOKEN_COOKIE_NAME, newTokens.getAccessToken(), false); // isLogout = false
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
	    
	    // Atualiza o valor do cookie para o Refresh Token no header da resposta (expiração longa).
	    ResponseCookie refreshCookie = cookieFactoryService
	    		.create(Const.REFRESH_TOKEN_COOKIE_NAME, newTokens.getRefreshToken(), false); // isLogout = false
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());  

	    // Não precisa retornar nenhum conteúdo no body, porque os cookies vão no header da resposta.
	    return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/logout")
	@Operation(
    	summary = "Logout da aplicação", 
    	description = "Endpoint para realzar o encerramento do acesso do usuário mantido pelo Keycloak.",
    	tags = {TAG}
    )
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
	    // Extrai o refresh token dos cookies (necessário para o Keycloak invalidar a sessão)
	    String refreshToken = cookieFactoryService.extractCookie(request, Const.REFRESH_TOKEN_COOKIE_NAME);

	    // Notifica o Keycloak (Opcional, mas recomendado para SSO)
	    if (refreshToken != null) {
	        authService.logoutKeycloak(refreshToken);
	    }

	    // Exclui os Cookies HttpOnly no navegador (Access e Refresh)
	    ResponseCookie deleteAccessCookie = cookieFactoryService
        		.create(Const.ACCESS_TOKEN_COOKIE_NAME, "", true); // isLogout = true
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, deleteAccessCookie.toString());
	    
	    ResponseCookie deleteRefreshCookie = cookieFactoryService
        		.create(Const.REFRESH_TOKEN_COOKIE_NAME, "", true); // isLogout = true
	    
	    response.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshCookie.toString());

	    // Redireciona para o logout do Keycloak (para limpar a sessão do navegador no IDM)
	    String keycloakLogoutUrl = 
	    		keycloakProperties.getKeycloak().getIssuer() + "/protocol/openid-connect/logout" +
	            "?client_id=" + keycloakProperties.getKeycloak().getClientId() +
	            "&post_logout_redirect_uri=" + frontendLoginPage;

	    return ResponseEntity.status(HttpStatus.FOUND)
	            .location(URI.create(keycloakLogoutUrl)) 
	            .build();
	}
	
	@GetMapping("/me")
	@Operation(
    	summary = "Informações do usuário autenticado", 
    	description = "Endpoint para retornar os dados do usuário autenticado no Keycloak.",
    	tags = {TAG}
    )
	public ResponseEntity<KeycloakAuthenticatedUserResponse> me() {
		// Obtém os dados do usuário autenticado a partir do contexto de segurança (Spring Security).
		AuthenticatedUser user = authenticatedUserService.getCurrentUser();	
		return ResponseEntity.ok(authenticatedUserMapper.toDtoFromDomain(user));	
	}
}
