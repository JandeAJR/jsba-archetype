#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Classe: Application<br>
 * Pacote: ${package}.application
 *
 * <p>Starter da aplicação Java Spring Boot.<br>
 * JSBA - Java Spring Boot Application Project</p>
 * 
 *<p><a>https://spring.io/projects/spring-boot</a><br>
 *<a>https://spring.io/projects/spring-cloud-openfeign</a></p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients(
		basePackages = "${package}.application.external.http.feign"
)           
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
