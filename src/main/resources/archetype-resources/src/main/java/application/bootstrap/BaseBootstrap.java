#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.bootstrap;

import org.springframework.boot.CommandLineRunner;

import ${package}.application.shared.util.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe: BaseBootstrap <br>
 * Pacote: ${package}.application.core.bootstrap
 *
 * <p>Classe base para as demais classes de bootstrap que irão rodar os testes iniciais da aplicação.</p>
 * 
 * <p>Implementa {@link CommandLineRunner}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Slf4j
public abstract class BaseBootstrap implements CommandLineRunner {
	protected abstract void startRunning();
	
	protected void writeLog(String profile, String authType, String moduleMessage) {
		writeLog(profile, authType, moduleMessage, "");
	}
	
	protected void writeLog(String profile, String authType, String moduleMessage, String symbol) {
		symbol = (symbol == null) ? Const.LEFT_ARROWS : symbol;		
        log.info(Const.ANSI_GREEN + Const.ANSI_BOLD + symbol + "Profile em execução: " + profile + Const.ANSI_RESET);
        log.info(Const.ANSI_GREEN + Const.ANSI_BOLD + symbol + "Estratégia de autenticação: " + authType + Const.ANSI_RESET);
        log.info(Const.ANSI_GREEN + Const.ANSI_BOLD + symbol + moduleMessage + Const.ANSI_RESET);
	}
	
    public void run(String... args) throws Exception {}
}
