#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.bootstrap.failure;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * Classe: FailureAnalyzer <br>
 * Pacote: ${package}.application.core.bootstrap.failure
 *
 * <p>Classe para carregar a árvore de exceções durante a inicialização da aplicação, e capturar erros específicos.
 * Traduzindo esses erros para mensagens mais amigáveis. <br>
 * Todas essas traduções devem ser implementadas nesta classe.</p>
 * 
 * <p>Estende de {@link AbstractFailureAnalyzer}.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class FailureAnalyzer extends AbstractFailureAnalyzer<Exception> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Exception cause) {
        // Carrega a String com a árvore de exceções
        String message = rootFailure.toString();
        
        // Busca na árvore de exceções a mensagem específica do JwtDecoder/Issuer
        // Específico para a estratégia de autenticação externa (OAuth2/IDM)
        if (message.contains("jwtDecoder") || message.contains("Unable to resolve the Configuration with the provided Issuer")) {
            return new FailureAnalysis(
                "Falha ao conectar com o provedor de identidade (IDM/IAM).",
                "Certifique-se de que o provedor de identidade (Keycloak, Microsoft Entra ID ou outro qualquer) " +
                "esteja disponível na url configurada para a aplicação, e que o Realm existe. " +
                "Verifique também se não há bloqueios de rede ou de firewall.",
                rootFailure
            );
        }
        
        return null;
    }
}
