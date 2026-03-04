#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;

/**
 * Class: AuthenticatedUser <br>
 * Pacote: ${package}.security.common.domain.model
 *
 * <p>Modelo com os atributos do usuário autenticado, utilizado para representar as informações 
 * do usuário atualmente autenticado no contexto de segurança da aplicação.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Data
public class AuthenticatedUser {
	private String id;
	private String userName;
	private String email;
	private String name;
	private Boolean emailVerified;
	private Boolean enabled;
	private OffsetDateTime dateCreated;	
	private List<String> roles;
}
