#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.service;

import ${package}.security.jwt.core.api.dto.request.JwtAuthRequest;
import ${package}.security.jwt.core.api.dto.response.JwtAuthenticatedUserResponse;
import ${package}.security.jwt.core.api.dto.response.JwtTokensResponse;
import ${package}.security.jwt.core.infrastructure.entity.RefreshTokenEntity;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;

/**
 * Interface: JwtAuthService <br>
 * Pacote: ${package}.security.jwt.core.service
 *
 * <p>Interface para fornecer o contrato dos serviços de autenticação na aplicação. <br>
 * Esse serviço implementa as funcionalidades de acordo com as práticas do framework Spring Security</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface JwtAuthService {
	JwtTokensResponse authenticate(JwtAuthRequest request, String userAgent);
	JwtTokensResponse refreshToken(String refreshToken, String userAgent);
	void logout(String refreshToken);
	JwtAuthenticatedUserResponse getMe(UserEntity userEntity);
	void detectReuseAttack(RefreshTokenEntity refreshTokenEntity);
}
