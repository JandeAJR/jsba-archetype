#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: UserResponse<br>
 * Pacote: ${package}.security.jwt.core.api.dto.response
 *
 * <p>Classe DTO (response) para retornar as informações do usuário autenticado na aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
@JsonPropertyOrder({ 
	"id", 
	"userName", 
	"email",
	"name",
	"accountNonExpired",
	"accountNonLocked", 
	"credentialsNonExpired", 
	"emailVerified",
	"enabled", 
	"dateCreated",
	"roles" })
public class UserResponse {
	private UUID id;
	private String userName;
	private String email;
	private String name;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean emailVerified;
	private Boolean enabled;
	private OffsetDateTime dateCreated;	
	private List<String> roles;
}
