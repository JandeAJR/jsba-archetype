#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: UserRequest<br>
 * Pacote: ${package}.security.jwt.core.api.dto.request
 *
 * <p>Classe DTO (request) recebe as informações do usuário.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
@AllArgsConstructor
public class UserRequest {
	private static final String USERNAME_REQUIRED = "O campo UserName é obrigatório.";
	private static final String USERNAME_SIZE = "O campo UserName deve ter no mínimo 3 e no máximo 30 caracteres.";
	
	private static final String EMAIL_REQUIRED = "O campo Email é obrigatório.";
	private static final String EMAIL_SIZE = "O campo email deve ter deve ter no mínimo 3 e no máximo 255 caracteres.";
	private static final String EMAIL_INVALID = "O email informado é inválido.";
	
	private static final String NAME_REQUIRED = "O campo Nome do Usuário é obrigatório.";
	private static final String NAME_SIZE = "O campo UserName deve ter no mínimo 3 e no máximo 100 caracteres.";
	
	private static final String PASSWORD_REQUIRED = "O campo Senha é obrigatório.";
	private static final String PASSWORD_SIZE = "O campo Senha deve ter no mínimo 8 e no máximo 255 caracteres.";
	
	private static final String ROLES_REQUIRED = "Informe as Roles para este Usuário.";

	@NotBlank(message = USERNAME_REQUIRED)
	@Size(min = 3, max = 30, message = USERNAME_SIZE)
	private String userName;
	
	@Email(message = EMAIL_INVALID)
	@NotBlank(message = EMAIL_REQUIRED)
	@Size(min = 3, max = 255, message = EMAIL_SIZE)
	private String email;
	
	@NotBlank(message = NAME_REQUIRED)
	@Size(min = 3, max = 100, message = NAME_SIZE)
	private String name;
	
	@NotBlank(message = PASSWORD_REQUIRED)
	@Size(min = 8, max = 255, message = PASSWORD_SIZE)
	private String password;
	
	@NotNull
	private Boolean acountNonExpired;
	
	@NotNull
	private Boolean accountNonLocked;
	
	@NotNull
	private Boolean credentialsNonExpired;
	
	@NotNull
	private Boolean emailVerified;

	@NotNull
	private Boolean enabled;
	
	@Valid
	@NotEmpty(message = ROLES_REQUIRED) 
	private List<String> roles;
}
