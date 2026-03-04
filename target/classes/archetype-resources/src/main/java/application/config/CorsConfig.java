#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ${package}.application.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;

/**
 * Classe: CorsConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável pela configuração de CORS (Cross-Origin Resource Sharing) para a aplicação. <br>
 * Define quais origens, métodos HTTP e headers são permitidos nas requisições feitas a partir de domínios diferentes.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@RequiredArgsConstructor
public class CorsConfig {
	private final CorsProperties corsProperties;
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Configuração de CORS
    	CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(corsProperties.allowedOrigins());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));        
        config.setAllowCredentials(true); // Permite envio de cookies / authorization header

        // Aplica a configuração para todas as rotas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
