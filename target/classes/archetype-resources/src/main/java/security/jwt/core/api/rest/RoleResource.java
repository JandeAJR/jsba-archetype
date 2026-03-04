#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ${package}.application.shared.util.BusinessConst;
import ${package}.application.shared.util.Const;
import ${package}.application.shared.wrapper.ResourceHatoasResponse;
import ${package}.security.jwt.core.api.dto.request.RoleRequest;
import ${package}.security.jwt.core.api.dto.response.RoleResponse;
import ${package}.security.jwt.core.api.mapper.ApiRoleMapper;
import ${package}.security.jwt.core.api.rest.assembler.RoleResourceHatoasAssembler;
import ${package}.security.jwt.core.api.rest.assembler.RoleResponseAssembler;
import ${package}.security.jwt.core.application.query.ListAllRolesQuery;
import ${package}.security.jwt.core.application.usecase.role.CreateRoleUseCase;
import ${package}.security.jwt.core.application.usecase.role.FindRoleByIdUseCase;
import ${package}.security.jwt.core.application.usecase.role.FindRoleByNameUseCase;
import ${package}.security.jwt.core.application.usecase.role.input.RoleInput;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Classe: RoleResource<br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para requisições http relacionadas ao domínio Roles.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class RoleResource {
	private final CreateRoleUseCase createRoleUseCase;
	private final FindRoleByIdUseCase findRoleByIdUseCase;
	private final FindRoleByNameUseCase findRoleByNameUseCase;
	private final ListAllRolesQuery listAllRolesQuery;	
	private final RoleResourceHatoasAssembler hatoasAssembler;
	private final RoleResponseAssembler responseAssembler;
	private final ApiRoleMapper mapper;
	
	@PostMapping
	public ResponseEntity<ResourceHatoasResponse<EntityModel<RoleResponse>>> create(
			@Valid @RequestBody RoleRequest request) {
		
		// Executa o caso de uso Cadastrar nova Role
		RoleOutput output = createRoleUseCase
				.execute(new RoleInput(request.getName(), request.getDescription()));
		
		// Mapper de output para DTO response para montar o recurso com HATOAS
		RoleResponse dto = mapper.toDtoFromApplicationOutput(output);
		
		// Monta o URI do recurso criado (opcional, mas recomendado para POST)
		URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
		
		// Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<RoleResponse> resource = hatoasAssembler.toModel(dto);
    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<RoleResponse>> responseBody = new ResourceHatoasResponse<>();
    	responseBody.setStatus(HttpStatus.CREATED.getReasonPhrase());
    	responseBody.setMessage(BusinessConst.ROLE_CREATED);
    	responseBody.setData(resource);
    	responseBody.setMetadata(null); // Não há paginação de dados aqui
    	
    	// Adiciona link global HATOAS (opcional)
	    responseBody.add(linkTo(methodOn(RoleResource.class)
	    		.findByName(dto.getName())).withRel("role-by-name").withType(HttpMethod.GET.toString()));

        return ResponseEntity.created(uri).body(responseBody);
	}
	
	@GetMapping
	public ResponseEntity<ResourceHatoasResponse<List<EntityModel<RoleResponse>>>> findAllPageable(
			@PageableDefault(
					page = 0, 
					size = Const.PAGE_SIZE, 
					sort = "name", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/roles
		//	  /api/${artifactId}/roles?page=0&size=20
		//    /api/${artifactId}/roles?page=0&size=20&sort=name
		//    /api/${artifactId}/roles?page=5&size=20&sort=name,desc
		//    /api/${artifactId}/roles?page=10&size=20&sort=name,asc&sort=description,desc
		
		// Executa o caso de uso Listar todas as Roles
		Page<RoleOutput> outputs = listAllRolesQuery.list(pageable); // com paginação
		
		// Mapper de outputs para DTOs response para montar o recurso com paginação e links HATOAS
		// Converte usando o MapStruct (que internamente mantém a paginação)
		Page<RoleResponse> dtos = mapper.toResponsePage(outputs);
		
		// Monta o corpo da resposta padronizada e com paginação de dados - HATOAS
		ResourceHatoasResponse<List<EntityModel<RoleResponse>>> responseBody =
				responseAssembler.toPagedResponse(
						dtos, 
						BusinessConst.ROLES_LIST, 
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				
		return ResponseEntity.ok(responseBody);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RoleResponse>> findAll() {
		Pageable unPaged = Pageable.unpaged(); // sem paginação
		
		// Executa o caso de uso Listar todas as Roles
		// Uso de getContent() para extrair a lista List<T> do objeto Page<T>, que é retornado pelo Caso de Uso
		List<RoleOutput> outputs = listAllRolesQuery.list(unPaged).getContent(); // Listar todos sem paginação
		
		// Mapper de outputs para DTOs response para montar o recurso (sem HATOAS)
		List<RoleResponse> responseBody = mapper.toDtoFromApplicationOutputList(outputs);
				
		return ResponseEntity.ok(responseBody);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ResourceHatoasResponse<EntityModel<RoleResponse>>> findById(@PathVariable String id) {
        // Executa o caso de uso Listar Role por Id
		RoleOutput output = findRoleByIdUseCase.execute(id);
        
		// Mapper de output para DTO response para montar o recurso com HATOAS
		RoleResponse dto = mapper.toDtoFromApplicationOutput(output);
        
        // Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<RoleResponse> resource = hatoasAssembler.toModel(dto);
    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<RoleResponse>> responseBody = new ResourceHatoasResponse<>();
    	responseBody.setStatus(HttpStatus.OK.getReasonPhrase());
    	responseBody.setMessage(BusinessConst.ROLE_BY_ID);
    	responseBody.setData(resource);
    	responseBody.setMetadata(null); // Não há paginação de dados aqui

        return ResponseEntity.ok(responseBody);
    }
	
	@GetMapping("/name/{name}")
    public ResponseEntity<ResourceHatoasResponse<EntityModel<RoleResponse>>> findByName(@PathVariable String name) {
        // Executa o caso de uso Listar Role pelo Nome
		RoleOutput output = findRoleByNameUseCase.execute(name);
        
		// Mapper de output para DTO response para montar o recurso com HATOAS
		RoleResponse dto = mapper.toDtoFromApplicationOutput(output);
        
        // Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<RoleResponse> resource = hatoasAssembler.toModel(dto);
    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<RoleResponse>> responseBody = new ResourceHatoasResponse<>();
    	responseBody.setStatus(HttpStatus.OK.getReasonPhrase());
    	responseBody.setMessage(BusinessConst.ROLE_BY_NAME);
    	responseBody.setData(resource);
    	responseBody.setMetadata(null); // Não há paginação de dados aqui

        return ResponseEntity.ok(responseBody);
    }
}
