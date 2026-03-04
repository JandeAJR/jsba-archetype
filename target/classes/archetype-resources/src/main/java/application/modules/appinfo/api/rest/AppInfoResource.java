#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.modules.appinfo.api.rest;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.application.config.properties.ProjectInfoProperties;
import ${package}.application.modules.appinfo.api.dto.response.AppInfoResponse;
import ${package}.application.shared.util.Const;
import lombok.RequiredArgsConstructor;

/**
 * Classe: AppInfoResource<br>
 * Pacote: ${package}.application.modules.appinfo.api.rest
 *
 * <p>Recurso REST que disponibiliza as informações da aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class AppInfoResource {
	private final ProjectInfoProperties projectInfoProperties;

    @GetMapping("/info")
    public ResponseEntity<AppInfoResponse> info() {  	
    	// Monta o response com as informações acerca da Aplicação
    	AppInfoResponse responseBody = AppInfoResponse.builder()
    			.name(Objects.toString(projectInfoProperties.name(), Const.UNKNOWN))
    			.version(Objects.toString(projectInfoProperties.version(), Const.UNKNOWN))
    			.database(Objects.toString(projectInfoProperties.database(), Const.NOT_CONFIGURED))
    			.profile(Objects.toString(projectInfoProperties.profile(), Const.UNKNOWN))
    			.build();
	  	
	  	return ResponseEntity.ok(responseBody);
    }
}
