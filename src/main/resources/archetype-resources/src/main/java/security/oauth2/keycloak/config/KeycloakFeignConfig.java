#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Classe: KeycloakFeignConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe para configurações específicas para a estratégia de autenticação KEYCLOAK.</p>
 * 
 * <p>Implementa {@link CommandLineRunner}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@ConditionalOnProperty(
		name = "security.strategy", 
		havingValue = "KEYCLOAK", 
		matchIfMissing = false
)
@EnableFeignClients(
		basePackages = "${package}.security.oauth2.keycloak.infrastructure.http.feign"
)
public class KeycloakFeignConfig {}
