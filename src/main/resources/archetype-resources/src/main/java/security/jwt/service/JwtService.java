#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.service;

import java.util.List;

import ${package}.security.jwt.core.infrastructure.entity.UserEntity;

/**
 * Interface: JwtService <br>
 * Pacote: ${package}.security.jwt.service
 *
 * <p>Interface reponsável pelo contrato do serviço JWT que gera e valida tokens JWT.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface JwtService {
	String generateAccessToken(UserEntity userEntity);
	String generateRefreshToken(String userName);
	String extractUserId(String token);
	String extractUsername(String token);
	String extractEmail(String token);
	String extractName(String token);
	List<String> extractRoles(String token);
}
