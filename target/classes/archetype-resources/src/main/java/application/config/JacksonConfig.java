#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Classe: JacksonConfig<br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe de configuração para o Jackson ObjectMapper, que é utilizado para serialização e desserialização de objetos JSON.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Configuration
public class JacksonConfig {
    @Bean
    ObjectMapper objectMapper() {
    	// Configura o ObjectMapper para lidar com tipos de data e hora do Java 8 (java.time) 
    	// e para não serializar datas como timestamps.
    	// Isso é importante para garantir que as datas sejam serializadas em um formato legível (como ISO-8601).
    	ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
