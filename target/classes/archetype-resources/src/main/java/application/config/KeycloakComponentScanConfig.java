#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe: KeycloakComponentScanConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável por configurar quais pacotes serão escaneados pelo Spring Boot 
 * para os módulos de autorização e autenticação externa à aplicação (externalauth).</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@ComponentScan(basePackages = {
		"${package}.security.common",
		"${package}.security.oauth2.keycloak"
})
@ConditionalOnProperty(
		name = "security.strategy", 
		havingValue = "KEYCLOAK", 
		matchIfMissing = false
)
public class KeycloakComponentScanConfig {}
