#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.application.shared.util.Const;
import ${package}.security.jwt.core.api.dto.response.UserRolesQueryResponse;
import ${package}.security.jwt.core.api.mapper.ApiUserRolesQueryMapper;
import ${package}.security.jwt.core.application.query.ListUserRolesQuery;
import ${package}.security.jwt.core.application.query.output.UserRolesQueryOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserRolesQueryResource <br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para requisições http relacionadas à queries (consultas, views e relatórios).</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/user-roles/query")
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class UserRolesQueryResource {
	private final ListUserRolesQuery userRolesQueryUserCase;
	private final ApiUserRolesQueryMapper userRolesQueryMapper;
	
	@GetMapping("/user-roles-jpql-query")
	public ResponseEntity<List<UserRolesQueryResponse>> selectUserRolesWithJpqlQuery(
			@RequestParam String username) {
		
		// Uri deste recurso:
		//	  /api/${artifactId}/user-roles/query/user-roles-jpql-query?username=admin
		
		Pageable unPaged = Pageable.unpaged(); // sem paginação
		
		// Executa o caso de uso Usuário e suas Roles (UserRoles Query)
		// Uso de getContent() para extrair a lista List<T> do objeto Page<T>, que é retornado pelo Caso de Uso
		List<UserRolesQueryOutput> outputs = userRolesQueryUserCase
				.listWithJpqlQuery(username, unPaged).getContent(); // Listar todos sem paginação
		
		// Mapper de outputs para DTOs response para montar o recurso (sem Hatoas)
		List<UserRolesQueryResponse> responseBody = userRolesQueryMapper.toDtoFromApplicationOutputList(outputs);
		
		return ResponseEntity.ok(responseBody);
	}
	
	@GetMapping("/user-roles-native-query")
	public ResponseEntity<List<UserRolesQueryResponse>> selectUserRolesWithNativeQuery(
			@RequestParam String username,
			@RequestParam(defaultValue = "false") Boolean exactly) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/user-roles/query/user-roles-native-query?name=ad
		//	  /api/${artifactId}/user-roles/query/user-roles-native-query?name=ad&exactly=false
		//	  /api/${artifactId}/user-roles/query/user-roles-native-query?name=admin&exactly=true
		
		Pageable unPaged = Pageable.unpaged(); // sem paginação
		
		// Executa o caso de uso Usuário e suas Roles (UserRoles Query)
		// Uso de getContent() para extrair a lista List<T> do objeto Page<T>, que é retornado pelo Caso de Uso
		List<UserRolesQueryOutput> outputs = userRolesQueryUserCase
				.listWithNativeQuery(username, exactly, unPaged).getContent(); // Listar todos sem paginação
		
		// Mapper de outputs para DTOs response para montar o recurso (sem Hatoas)
		List<UserRolesQueryResponse> responseBody = userRolesQueryMapper.toDtoFromApplicationOutputList(outputs);
		
		return ResponseEntity.ok(responseBody);
	}
}
