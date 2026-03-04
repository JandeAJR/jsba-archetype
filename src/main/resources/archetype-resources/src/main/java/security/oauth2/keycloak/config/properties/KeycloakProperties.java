#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: KeycloakProperties <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.config.properties
 *
 * <p>Classe reponsável por mapear a estrutura das propriedades de configuração 
 * para o Keycloak a partir do arquivo application-keycloak.yml.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@Validated
@Getter @Setter
@ConfigurationProperties(prefix = "security.oauth2")
public class KeycloakProperties {	
	@NotNull
	@Min(1)
	private Long expiration; // Access token (in minutes)
	
	@NotNull
	@Min(1)
	private Long refreshExpiration; // Refresh token (in minutes)
	
	private Keycloak keycloak = new Keycloak();
	
	@Getter @Setter
	public static class Keycloak {
		@NotBlank
		private String issuer; // Url do IDM para autenticação via OAuth2
		
		@NotBlank
		private String redirectUri; // Redirect URI cadastrada no IDM (Keycloak)
		
		@NotBlank
		private String clientId; // Client_id (audience) válido no token de autenticação
		
		@NotBlank
		private String clientSecret; // Segredo para o cliente (client_id/audience) cadastrado no IDM
		
		private String claim; // Identificação do nome da claim inserida no token de autenticação do IDM 
		                     // Essa propriedade pode ser null ou blank		
	}
}
