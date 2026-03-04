#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.bootstrap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ${package}.application.bootstrap.BaseBootstrap;
import ${package}.application.shared.util.Const;

/**
 * Classe: KeycloakBootstrap <br>
 * Pacote: ${package}.security.oauth2.keycloak.modules.oauth2.bootstrap
 *
 * <p><strong>Profile: test</strong> <br>
 * Executa a aplicação através do profile test (application-test.yml). <br>
 * Utiliza a estratégia de autenticação KEYCLOAK (security.strategy=KEYCLOAK no arquivo application-keycloak.yml).</p>
 *  
 * <p>Estende {@link BaseBootstrap}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
@Profile("test")
@ConditionalOnProperty(name = "security.strategy", havingValue = "KEYCLOAK", matchIfMissing = false)
public class KeycloakBootstrap extends BaseBootstrap {
	@Override
	protected void startRunning() {
		writeLog("test", Const.KEYCLOAK_IDENTIFIER, Const.OAUTH2_AUTHENTICATION_TEST_RUNNING_MESSAGE, null);
	}
	
	@Override
    public void run(String... args) throws Exception {
    	startRunning();    	
    }
}
