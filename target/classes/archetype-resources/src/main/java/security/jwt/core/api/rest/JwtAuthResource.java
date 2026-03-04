#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.application.shared.exception.global.RefreshTokenNotFoundException;
import ${package}.application.shared.util.Const;
import ${package}.security.jwt.core.api.dto.request.JwtAuthRequest;
import ${package}.security.jwt.core.api.dto.response.JwtTokensResponse;
import ${package}.security.jwt.core.api.dto.response.JwtAccessTokenResponse;
import ${package}.security.jwt.core.api.dto.response.JwtAuthenticatedUserResponse;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.service.JwtAuthService;
import ${package}.security.jwt.service.JwtCookieFactoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtAuthResource <br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para autenticação de usuários com JWT.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/auth")  
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class JwtAuthResource {
	private final JwtAuthService authService;
	private final JwtCookieFactoryService cookieFactoryService;
	
	// Tag para o Swagger UI
	private static final String TAG = "Jwt";

    @PostMapping("/login")
    @Operation(
    	summary = "Autenticação por token JWT", 
    	description = "Endpoint para realzar a autenticação internamente pela própria aplicação, itilizando token JWT.",
    	tags = {TAG}
    )
    public ResponseEntity<JwtAccessTokenResponse> login(
    		@RequestBody JwtAuthRequest request, 
    		HttpServletRequest httpRequest) {
    	
    	// Pega o user-agent da requisição http (para validação de possível tentativa de hijacking)
    	String userAgent = httpRequest.getHeader("User-Agent");
    	
    	// Realiza a autenticação do usuário para gerar o access token
    	JwtTokensResponse tokens = authService.authenticate(request, userAgent);
    	
    	// Criar novo cookie com refresh rotacionado
        ResponseCookie cookie = cookieFactoryService
        		.create(Const.REFRESH_TOKEN_COOKIE_NAME, tokens.getRefreshToken(), false); // isLogout = false
    	
        // Retorna o cookie com o refresh token e o body da resposta http com o token de acesso
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtAccessTokenResponse(tokens.getAccessToken()));
    }
    
    @PostMapping("/refresh")
    @Operation(
    	summary = "Solicitar um refresh token", 
    	description = "Endpoint para realzar a solicitação de um refresh token.",
    	tags = {TAG}
    )
    public ResponseEntity<JwtAccessTokenResponse> refresh(
    		@CookieValue(name = Const.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken,
    		HttpServletRequest httpRequest) {
    	
    	// Verfica se o refresh token foi informado no cookie de usuário
        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException(Const.REFRESH_TOKEN_COOKIE_VALUE_NOT_FOUND);
        }
        
        // Pega o user-agent da requisição http (para validação de possível tentativa de hijacking)
    	String userAgent = httpRequest.getHeader("User-Agent");

        // Gera o novo refresh token e o novo token de acesso.
    	// Ao gerar o novo refresh token é feita a validação primeiro para 
    	// ver se o refresh token está válido e se não está expirado.
        JwtTokensResponse tokens = authService.refreshToken(refreshToken, userAgent);

        // Criar novo cookie com refresh rotacionado
        ResponseCookie cookie = cookieFactoryService
        		.create(Const.REFRESH_TOKEN_COOKIE_NAME, tokens.getRefreshToken(), false); // isLogout = false

        // Retorna o novo cookie com o refresh token e o body da resposta http com o novo token de acesso
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtAccessTokenResponse(tokens.getAccessToken()));
    }
    
    @PostMapping("/logout")
    @Operation(
    	summary = "Logout da aplicação", 
    	description = "Endpoint para realzar o encerramento do acesso do usuário mantido pela aplicação.",
    	tags = {TAG}
    )
    public ResponseEntity<Void> logout(
    		@CookieValue(name = Const.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken) {
    	
    	// Verfica se o refresh token foi informado no cookie de usuário
    	if (refreshToken != null) {
            authService.logout(refreshToken);
        }
        
        // Cria o cookie de refresh token para exclusão
        ResponseCookie deleteCookie = cookieFactoryService
        		.create(Const.REFRESH_TOKEN_COOKIE_NAME, "", true); // isLogout = true

        // Retorna a resposta para a exclusão do cookie no navegador do usuário
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }
    
    @GetMapping("/me")
    @Operation(
    	summary = "Informações do usuário autenticado", 
    	description = "Endpoint para retornar os dados do usuário autenticado na aplicação.",
    	tags = {TAG}
    )
    public ResponseEntity<JwtAuthenticatedUserResponse> me(@AuthenticationPrincipal UserEntity userEntity) {  	
    	// Info: O parâmetro (@AuthenticationPrincipal UserEntity user) é utilizado para 
    	//       resolver o contexto do usuário autenticado na aplicação.
    	//       Para isso usa-se a anotação do @AuthenticationPrincipal, fornecida pelo Spring Security
    	return ResponseEntity.ok(authService.getMe(userEntity));
    }
}
