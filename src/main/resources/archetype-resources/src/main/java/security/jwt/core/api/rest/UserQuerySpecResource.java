#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ${package}.application.shared.util.BusinessConst;
import ${package}.application.shared.util.Const;
import ${package}.application.shared.wrapper.ResourceHatoasResponse;
import ${package}.security.jwt.core.api.dto.response.UserResponse;
import ${package}.security.jwt.core.api.mapper.ApiUserMapper;
import ${package}.security.jwt.core.api.rest.assembler.UserQuerySpecResponseAssembler;
import ${package}.security.jwt.core.application.query.filter.UserFilter;
import ${package}.security.jwt.core.application.query.spec.ListUsersByNameQuerySpec;
import ${package}.security.jwt.core.application.query.spec.ListUsersByUserNamesQuerySpec;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserQuerySpecResource <br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para requisições http relacionadas à queries spec.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/users/query/spec")
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class UserQuerySpecResource {
	private final ListUsersByNameQuerySpec listUsersByNameQuerySpec;
	private final ListUsersByUserNamesQuerySpec listUsersByUserNamesQuerySpec;
	private final UserQuerySpecResponseAssembler responseAssembler;
	private final ApiUserMapper mapper;
	
	@GetMapping("/byname")
	public ResponseEntity<ResourceHatoasResponse<List<UserResponse>>> listUsersByName(
			UserFilter filter,
			@PageableDefault(
					page = 0, 
					size = Const.PAGE_SIZE, 
					sort = "name", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/users/query/spec/byname?name=ad&operator=STARTS_WITH
		//	  /api/${artifactId}/users/query/spec/byname?name=ad&operator=STARTS_WITH&page=0&size=20
		//    /api/${artifactId}/users/query/spec/byname?name=ad&operator=STARTS_WITH&page=0&size=20&sort=username
		//    /api/${artifactId}/users/query/spec/byname?name=ad&operator=STARTS_WITH&page=5&size=20&sort=username,desc
		//    /api/${artifactId}/users/query/spec/byname?name=ad&operator=STARTS_WITH&page=10&size=20&sort=username,asc&sort=name,desc
		
		// Executa a query spec listar usuários pelo nome
		Page<UserOutput> outputs = listUsersByNameQuerySpec.list(filter, pageable);
		
		// Mapper de outputs para DTOs response para montar o recurso com paginação e links HATOAS
		// Converte usando o MapStruct (que internamente mantém a paginação)
		Page<UserResponse> dtos = mapper.toResponsePage(outputs);
		
		// Monta o corpo da resposta padronizada e com paginação de dados - HATOAS
		ResourceHatoasResponse<List<UserResponse>> responseBody =
				responseAssembler.toPagedResponse(
	                    dtos,
	                    BusinessConst.USERS_LIST,
	                    ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

	    return ResponseEntity.ok(responseBody);
	}
	
	@GetMapping("/byusernames")
	public ResponseEntity<ResourceHatoasResponse<List<UserResponse>>> listUsersByUsersNames(
			UserFilter filter,
			@PageableDefault(
					page = 0, 
					size = Const.PAGE_SIZE, 
					sort = "userName", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/users/query/spec/byusernames?usernames=admin,basic&operator=STARTS_WITH
		//	  /api/${artifactId}/users/query/spec/byusernames?usernames=admin,basic&operator=STARTS_WITH&page=0&size=20
		//    /api/${artifactId}/users/query/spec/byusernames?usernames=admin,basic&operator=STARTS_WITH&page=0&size=20&sort=username
		//    /api/${artifactId}/users/query/spec/byusernames?usernames=admin,basic&operator=STARTS_WITH&page=5&size=20&sort=username,desc
		//    /api/${artifactId}/users/query/spec/byusernames?usernames=admin,basic&operator=STARTS_WITH&page=10&size=20&sort=username,asc&sort=name,desc
		
		// Executa a query spec listar usuários pela lista de usernames
		Page<UserOutput> outputs = listUsersByUserNamesQuerySpec.list(filter, pageable);
		
		// Mapper de outputs para DTOs response para montar o recurso com paginação e links HATOAS
		// Converte usando o MapStruct (que internamente mantém a paginação)
		Page<UserResponse> dtos = mapper.toResponsePage(outputs);
		
		// Monta o corpo da resposta padronizada e com paginação de dados - HATOAS
		ResourceHatoasResponse<List<UserResponse>> responseBody =
				responseAssembler.toPagedResponse(
	                    dtos,
	                    BusinessConst.USERS_LIST,
	                    ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

	    return ResponseEntity.ok(responseBody);
	}
}
