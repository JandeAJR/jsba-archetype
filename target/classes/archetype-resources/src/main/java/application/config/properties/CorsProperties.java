#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Record: CorsProperties <br>
 * Pacote: ${package}.application.core.config.properties
 *
 * <p>Record para mapear o valor da propriedade "authentication.oauth2.strategies" 
 * que fica no arquivo application.yml</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@ConfigurationProperties(prefix = "cors")
public record CorsProperties(List<String> allowedOrigins) {
	public CorsProperties {
		if (allowedOrigins == null) {
			allowedOrigins = List.of("*");
		}
	}
}
