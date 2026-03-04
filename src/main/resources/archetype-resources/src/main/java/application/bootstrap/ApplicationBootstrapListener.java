#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.bootstrap;

import java.lang.management.ManagementFactory;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ${package}.application.shared.util.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe: ApplicationBootstrapListener <br>
 * Pacote: ${package}.application.core.bootstrap
 *
 * <p>Classe listener para mensurar o tempo de inicialização da aplicação.</p>
 * 
 * <p>Implementa {@link ApplicationListener}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Slf4j
@Component
public class ApplicationBootstrapListener implements ApplicationListener<ApplicationReadyEvent> {
	// Metadados da aplicação (nome da aplicação)
	@Value("${symbol_dollar}{spring.application.name:JSBA}")
	private String applicationName;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {		
		// Obtém o tempo de atividade da JVM (Uptime) até este exato momento
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        
        // Formata o tempo para segundos com precisão
        String timeString = NumberFormat.getNumberInstance(Locale.getDefault())
                .format((double) uptime / 1000);

        // Log no servidor
        log.info(Const.ANSI_GREEN + Const.ANSI_BOLD + Const.LEFT_ARROWS + "{} - em execução" + Const.ANSI_RESET, applicationName.toUpperCase());
        log.info(Const.ANSI_GREEN + Const.ANSI_BOLD + Const.LEFT_ARROWS + "Tempo de inicialização: {} segundos" + Const.ANSI_RESET, timeString);	
	}
}
