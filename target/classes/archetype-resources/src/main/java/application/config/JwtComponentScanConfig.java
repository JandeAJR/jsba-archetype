#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe: JwtComponentScanConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável por configurar quais pacotes serão escaneados pelo Spring Boot 
 * para os módulos de autorização e autenticação interna da aplicação (jwt).</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@ComponentScan(basePackages = {
		"${package}.security.common",
	    "${package}.security.jwt"
})
@EnableJpaRepositories(
		basePackages = "${package}.security.jwt.core.infrastructure.repository"
)
@EntityScan(
		basePackages = "${package}.security.jwt.core.infrastructure.entity"
)
@ConditionalOnProperty(
		name = "security.strategy", 
		havingValue = "JWT", 
		matchIfMissing = false
)
public class JwtComponentScanConfig {}
