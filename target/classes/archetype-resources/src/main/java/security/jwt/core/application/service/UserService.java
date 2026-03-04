#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ${package}.security.jwt.core.api.dto.request.UserRequest;
import ${package}.security.jwt.core.application.usecase.role.FindRoleByNameUseCase;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.application.usecase.user.CreateUserUseCase;
import ${package}.security.jwt.core.application.usecase.user.input.UserInput;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserService<br>
 * Pacote: ${package}.security.jwt.core.application.service
 * 
 * <p>Classe responável por orquestrar os casos de uso relacionados ao usuário (UserUsecase).</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Service
@RequiredArgsConstructor
public class UserService {
	private final CreateUserUseCase createUserUseCase;
	private final FindRoleByNameUseCase findRoleByNameUseCase;
	
	public UserOutput createUser(UserRequest userRequest) {
		// Recupera a(s) RoleOutput(s) através da lista de roleName(s) 
		// vindas da requisição para montar o input do caso de uso Cadastrar Usuário.
		List<RoleOutput> rolesOutput = userRequest.getRoles() // Nome(s) da(s) role(s) vindo(s) da requisição (List<String>).
			.stream()
		    .map(findRoleByNameUseCase::execute) 
		    .filter(Objects::nonNull)           
		    .toList();  
				
		// Executa o caso de uso Cadastrar Usuário passando o input com os dados da requisição e as roles mapeadas
		return createUserUseCase
			.execute(new UserInput(
					userRequest.getUserName(), 
					userRequest.getEmail(),
					userRequest.getName(),
					userRequest.getPassword(),
					userRequest.getAcountNonExpired(),
					userRequest.getAccountNonLocked(),
					userRequest.getCredentialsNonExpired(),
					userRequest.getEmailVerified(),
					userRequest.getEnabled(),
					rolesOutput));
	}
}
