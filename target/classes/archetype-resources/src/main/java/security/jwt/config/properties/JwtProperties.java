#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: JwtProperties <br>
 * Pacote: ${package}.security.jwt.core.config.properties
 *
 * <p>Classe reponsável por mapear a estrutura das propriedades de configuração do JWT 
 * a partir do arquivo application-jwt.yml. <br>
 * Contém a chave secreta e o tempo de expiração dos tokens JWT.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@Validated
@Getter @Setter
@ConfigurationProperties(prefix = "security")
public class JwtProperties {
	private Jwt jwt = new Jwt();
	private Auth auth = new Auth();
	private BCrypt bcrypt = new BCrypt();
	
	@Getter @Setter
	public static class Jwt { // security.jwt
		@NotBlank
		private String key; // Private key for signing JWT
		
		@NotNull
		@Min(1)
		private Long expiration; // Access token (in minutes)
		
		@NotNull
		@Min(1)
		private Long refreshExpiration; // Refresh token (in minutes)		
	}
	
	@Getter @Setter
	public static class Auth { // security.auth
		@NotBlank
		private String loginPath; // Path for endpoint login (e.g., "/auth/login")	
	}
	
	@Getter @Setter
	public static class BCrypt { // security.bcrypt
		@NotNull
		@Min(10) @Max(12)
		private Long strength; // Strength for BCrypt password encoder (e.g., 12)
	}
}
