#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.modules.appinfo.api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: AppInfoResponse<br>
 * Pacote: ${package}.application.modules.appinfo.api.dto.response
 *
 * <p>Classe DTO (response) para retornar as informações da aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Builder
@Getter @Setter
@JsonPropertyOrder({ "name", "version", "database", "profile" })
public class AppInfoResponse {
	private String name;
	private String version;
	private String database;
	private String profile;
}
