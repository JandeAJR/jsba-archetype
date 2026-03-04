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
import ${package}.security.jwt.core.api.dto.response.RoleResponse;
import ${package}.security.jwt.core.api.mapper.ApiRoleMapper;
import ${package}.security.jwt.core.api.rest.assembler.RoleQuerySpecResponseAssembler;
import ${package}.security.jwt.core.application.query.filter.RoleFilter;
import ${package}.security.jwt.core.application.query.spec.ListRolesByNamesQuerySpec;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Classe: RoleQuerySpecResource <br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para requisições http relacionadas à queries spec.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/roles/query/spec")
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class RoleQuerySpecResource {
	private final ListRolesByNamesQuerySpec listRolesByNamesQuerySpec;
	private final RoleQuerySpecResponseAssembler responseAssembler;
	private final ApiRoleMapper mapper;
	
	@GetMapping("/bynames")
	public ResponseEntity<ResourceHatoasResponse<List<RoleResponse>>> listRolesByNames(
			RoleFilter filter,
			@PageableDefault(
					page = 0, 
					size = Const.PAGE_SIZE, 
					sort = "name", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/roles/query/spec/bynames?names=ROLE_ADMIN,ROLE_BASIC_USER&operator=STARTS_WITH
		//	  /api/${artifactId}/roles/query/spec/bynames?names=ROLE_ADMIN,ROLE_BASIC_USER&operator=STARTS_WITH&page=0&size=20
		//    /api/${artifactId}/roles/query/spec/bynames?names=ROLE_ADMIN,ROLE_BASIC_USER&operator=STARTS_WITH&page=0&size=20&sort=username
		//    /api/${artifactId}/roles/query/spec/bynames?names=ROLE_ADMIN,ROLE_BASIC_USER&operator=STARTS_WITH&page=5&size=20&sort=username,desc
		//    /api/${artifactId}/roles/query/spec/bynames?names=ROLE_ADMIN,ROLE_BASIC_USER&operator=STARTS_WITH&page=10&size=20&sort=username,asc&sort=name,desc
		
		// Executa a query spec listar roles pela lista de nomes
		Page<RoleOutput> outputs = listRolesByNamesQuerySpec.list(filter, pageable);
		
		// Mapper de outputs para DTOs response para montar o recurso com paginação e links HATOAS
		// Converte usando o MapStruct (que internamente mantém a paginação)
		Page<RoleResponse> dtos = mapper.toResponsePage(outputs);
		
		// Monta o corpo da resposta padronizada e com paginação de dados - HATOAS
		ResourceHatoasResponse<List<RoleResponse>> responseBody =
				responseAssembler.toPagedResponse(
	                    dtos,
	                    BusinessConst.ROLES_LIST,
	                    ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

	    return ResponseEntity.ok(responseBody);
	}
}
