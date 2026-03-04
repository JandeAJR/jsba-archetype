#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ${package}.security.common.domain.model.AuthenticatedUser;
import lombok.AllArgsConstructor;

/**
 * Classe: JwtAuthenticatedUserResponse<br>
 * Pacote: ${package}.security.jwt.core.api.dto.response
 *
 * <p>Classe DTO (response) para retornar as informações do usuário autenticado. <br>
 * Extende {@link AuthenticatedUser}.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@AllArgsConstructor
@JsonPropertyOrder({ 
	"id", 
	"userName", 
	"email",
	"name", 
	"emailVerified", 
	"enabled", 
	"dateCreated",
	"roles" })
public class JwtAuthenticatedUserResponse extends AuthenticatedUser {}
