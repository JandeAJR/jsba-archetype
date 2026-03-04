#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import ${package}.application.config.properties.AuthenticationProperties;
import ${package}.application.shared.exception.global.SecurityStrategyNotDefinedException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Classe: SecurityValidationConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável pela configuração da estratégia de autenticação da aplicação. <br>
 * Os valores aceitos são definidos no arquivo application.yml</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@RequiredArgsConstructor
public class SecurityValidationConfig {	
	private final AuthenticationProperties authenticationProperties;
	
	private static final String TEXT_SEPARATOR = 
			"================================================================${symbol_escape}n";
	
	@Value("${symbol_dollar}{security.strategy:NOT_DEFINED}")
    private String securityStrategy;
	
	@PostConstruct
    public void validateSecurityStrategy() {  
		List<String> validStrategies = authenticationProperties.strategies();
		
        if (!validStrategies.contains(securityStrategy.toUpperCase())) {
            throw new SecurityStrategyNotDefinedException(
                "${symbol_escape}n${symbol_escape}n" +
                TEXT_SEPARATOR +
                " ERRO DE CONFIGURAÇÃO NO JSBA TEMPLATE ${symbol_escape}n" +
                TEXT_SEPARATOR +
                " Nenhuma estratégia de segurança válida foi detectada!${symbol_escape}n" +
                " Defina um valor em 'security.strategy' no respectivo arquivo .yml.${symbol_escape}n" +
                " Valores aceitos: " + String.join(", ", validStrategies) + "${symbol_escape}n" +
                " Valor para 'security.strategy' atual: " + securityStrategy + "${symbol_escape}n" +
                TEXT_SEPARATOR
            );
        }
    }
}
