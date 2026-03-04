#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: UserRolesQueryResponse<br>
 * Pacote: ${package}.security.jwt.core.api.dto.response
 *
 * <p>Classe DTO (response) retorna as informações da consulta usuários e suas roles (UserRoles Query).</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
@JsonPropertyOrder({ 
	"userId", 
	"userName",
	"email",
	"name", 
	"enabled",
	"dateCreated",
	"roleName",
	"roleDescription" })
public class UserRolesQueryResponse {
	private UUID userId;
	private String userName;
	private String email;
	private String name;
	private Boolean enabled;		
	private OffsetDateTime dateCreated;		
	private String roleName;
	private String roleDescription;
}
