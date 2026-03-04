#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.application.shared.exception.global.RefreshTokenException;
import ${package}.application.shared.util.Const;
import ${package}.security.jwt.config.properties.JwtProperties;
import ${package}.security.jwt.core.api.dto.request.JwtAuthRequest;
import ${package}.security.jwt.core.api.dto.response.JwtAuthenticatedUserResponse;
import ${package}.security.jwt.core.api.dto.response.JwtTokensResponse;
import ${package}.security.jwt.core.infrastructure.entity.RefreshTokenEntity;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.core.infrastructure.mapper.InfrastructureAuthMapper;
import ${package}.security.jwt.core.infrastructure.repository.RefreshTokenJpaRepository;
import ${package}.security.jwt.service.JwtAuthService;
import ${package}.security.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtAuthServiceImpl<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.service.impl
 *
 * <p>Classe de serviço para implementar o contrato de autenticação fornecido pela interface {@link JwtAuthService}.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {
	private final RefreshTokenJpaRepository refreshTokenJpaRepository;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	private final JwtProperties jwtProperties;
	private final JwtBCryptHashingServiceImpl encoder; 
	private final InfrastructureAuthMapper mapper;

	@Override
	@Transactional
	public JwtTokensResponse authenticate(JwtAuthRequest request, String userAgent) {
		// Realiza a autenticação do usuário.
    	Authentication auth = authManager.authenticate(
    		new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );
    	
    	// Retorna o usuário autenticado (contexto do Spring Security).
    	UserEntity userEntity = Optional.ofNullable(auth.getPrincipal())
    		    .filter(UserEntity.class::isInstance)
    		    .map(UserEntity.class::cast)
    		    .orElseThrow(() -> new UsernameNotFoundException(Const.USER_CONTEXT_NOT_FOUND));
        
        // Gera o token de acesso (access token).
        String accessToken = jwtService.generateAccessToken(userEntity);
        
        // Gera o refresh token.
        String rawRefreshToken = jwtService.generateRefreshToken(userEntity.getUsername());
        
        // Gera o hash do refresh token para persistir no repositório de dados.
        String hashedRefreshToken = encoder.hashSha256Hex(rawRefreshToken);
        
        // Cria a entidade JPA RefreshTokenEntity para persistir os dados do 
        // refresh token no repositório de dados.
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
        		.id(hashedRefreshToken)
        		.userId(userEntity.getId())
        		.userEntity(userEntity)
        		.userAgentHash(encoder.hashSha256Hex(userAgent))
        		.revoked(false)
        		.expiresAt(OffsetDateTime.now().plusMinutes(jwtProperties.getJwt().getRefreshExpiration()))
        		.build();
        
        // Salva a entidade RefreshToken no repositório de dados.
        refreshTokenJpaRepository.save(refreshTokenEntity);
        
        // Retorna o token de acesso e o refresh token.
        // O refresh token será armazenado em um cookie de usuário.
        return new JwtTokensResponse(accessToken, rawRefreshToken);        
	}

	@Override
	@Transactional
	public JwtTokensResponse refreshToken(String refreshToken, String userAgent) {
		// Valida o refresh token vindo da requisição.
		// Verificando se o refresh token é inválido ou não está expirado.
		RefreshTokenEntity storedToken = validate(refreshToken, userAgent);

	    // Revoga o antigo refresh token.
		storedToken.setRevoked(true);

	    // Gera um novo refresh token.
		String userName = jwtService.extractUsername(refreshToken);
	    String newRawRefreshToken = jwtService.generateRefreshToken(userName);
	    String newHashedRefreshToken = encoder.hashSha256Hex(newRawRefreshToken); // Hash do novo refresh token 
	                                                                             // para persistir no repositório de dados.
	    
	    // Cria a nova entidade JPA RefreshTokenEntity para persistir os dados do novo
	    // refresh token no repositório de dados.
        RefreshTokenEntity newRefreshTokenEntity = RefreshTokenEntity.builder()
        		.id(newHashedRefreshToken)
        		.userId(storedToken.getUserId())
        		.userEntity(storedToken.getUserEntity())
        		.userAgentHash(encoder.hashSha256Hex(userAgent))
        		.revoked(false)
        		.expiresAt(OffsetDateTime.now().plusMinutes(jwtProperties.getJwt().getRefreshExpiration()))
        		.build();

        // Salva a nova entidade RefreshToken no repositório de dados.
	    refreshTokenJpaRepository.save(newRefreshTokenEntity);

	    // Gera o novo token de acesso (access token).
	    String newAccessToken = jwtService.generateAccessToken(storedToken.getUserEntity());

	    // Retorna o novo token de acesso e o novo refresh token.
	    // O novo refresh token será armazenado em um cookie de usuário.
	    return new JwtTokensResponse(newAccessToken, newRawRefreshToken);
	}

	@Override
	@Transactional
	public void logout(String refreshToken) { 
		// Na operação de logout o refresh token do usuário é revogado.
		if (refreshToken != null) {
			// Gera o hash do refresh token para consulta no repositório de dados.
			String refreshTokenHashed = encoder.hashSha256Hex(refreshToken);
			
			// Revoga o refresh token (se existir no repositório de dados). 
			refreshTokenJpaRepository
				.findById(refreshTokenHashed)
				.ifPresent(token -> token.setRevoked(true));
		}
	}

	@Override
	public JwtAuthenticatedUserResponse getMe(UserEntity userEntity) {
		// Retorna o mapper de UserEntity para AuthRespose.
    	return mapper.toDtoFromEntity(userEntity); 
	}
	
	@Override
	@Transactional
	public void detectReuseAttack(RefreshTokenEntity refreshTokenEntity) {
		// Se houver uma tentativa de enviar um refresh token que já foi revogado,
		// todos os refresh tokens deste usuário serão revogados (possível ataque de hijacking).
		// Ref: https://en.wikipedia.org/wiki/Session_hijacking
	    refreshTokenJpaRepository.revokeAllByUserId(refreshTokenEntity.getUserId());
	    throw new SecurityException(Const.HIJACKING_SECURITY_FAIL);
	}
		
	// Valida o refresh token vindo do cookie de sessão do usuário durante a requisição http.
	// Verifica se o refresh existe e é se é válido (possível ataque de hijacking). 
	private RefreshTokenEntity validate(String rawRefreshToken, String currentUserAgent) {
		// Gera o hash do refresh token a ser validado para consulta no repositório de dados.
		String refreshTokenHashed = encoder.hashSha256Hex(rawRefreshToken);

		// Se não encontrar o refresh token no repositório de dados levanta a exception Refresh Token Inválido.
	    RefreshTokenEntity storedRefreshToken = refreshTokenJpaRepository
	            .findById(refreshTokenHashed)
	            .orElseThrow(() -> new RefreshTokenException(Const.INVALID_AUTH_REFRESH));

	    // Se o refresh token existem, porém está revogado, pode ser um possível ataque por reuso (hijacking attack).
	    if (Boolean.TRUE.equals(storedRefreshToken.getRevoked())) {
	        detectReuseAttack(storedRefreshToken);
	    }

	    // Verifica se o refresh token está expirado.
	    if (storedRefreshToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
	        throw new RefreshTokenException(Const.EXPIRED_AUTH_REFRESH);
	    }

	    // Gera o hash do User-Agent para consulta no repositório de dados.
	    String currentAgentHash = encoder.hashSha256Hex(currentUserAgent);

	    // Se for uma requisição de um mesmo refresh token, mas com um User-Agent diferente,
	    // pode ser um possível ataque pr reuso (hijacking attack).
	    if (!storedRefreshToken.getUserAgentHash().equals(currentAgentHash)) {
	        detectReuseAttack(storedRefreshToken);
	    }

	    // Se todas as validações forem ok, retorna a entidade do refresh token armazenado no repositório de dados.
	    return storedRefreshToken;
	}
}
