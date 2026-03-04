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
import ${package}.security.jwt.core.api.dto.request.UserRequest;
import ${package}.security.jwt.core.api.dto.response.UserResponse;
import ${package}.security.jwt.core.api.mapper.ApiUserMapper;
import ${package}.security.jwt.core.api.rest.assembler.UserResourceHatoasAssembler;
import ${package}.security.jwt.core.api.rest.assembler.UserResponseAssembler;
import ${package}.security.jwt.core.application.query.ListAllUsersQuery;
import ${package}.security.jwt.core.application.service.UserService;
import ${package}.security.jwt.core.application.usecase.user.FindUserByIdUseCase;
import ${package}.security.jwt.core.application.usecase.user.FindUserByUserNameUseCase;
import ${package}.security.jwt.core.application.usecase.user.output.UserOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserResource<br>
 * Pacote: ${package}.security.jwt.core.api.rest
 *
 * <p>Recurso (controller) REST para requisições http relacionadas ao domínio Users.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class UserResource {
	private final UserService userService;
	private final FindUserByIdUseCase findUserByIdUseCase;
	private final FindUserByUserNameUseCase findUserByUserNameUseCase;
	private final ListAllUsersQuery listAllUsersQuery;	
	private final UserResourceHatoasAssembler hatoasAssembler;
	private final UserResponseAssembler responseAssembler;
	private final ApiUserMapper mapper;	
	
	@PostMapping
	public ResponseEntity<ResourceHatoasResponse<EntityModel<UserResponse>>> create(
			@Valid @RequestBody UserRequest request) {
					
		// Utiliza o serviço Criar Usuário passando o input com os dados da requisição.
		// Este serviço irá orquestrar os casos de uso Listar Roles e Criar Usuário.
		UserOutput output = userService.createUser(request);
		
		// Mapper de output para DTO response para montar o recurso com HATOAS
		UserResponse dto = mapper.toDtoFromApplicationOutput(output);
		
		// Monta o URI do recurso criado (opcional, mas recomendado para POST)
		URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
		
		// Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<UserResponse> resource = hatoasAssembler.toModel(dto);
    	    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<UserResponse>> responseBody = ResourceHatoasResponse.<EntityModel<UserResponse>>builder()
    		    .status(HttpStatus.CREATED.getReasonPhrase())
    		    .message(BusinessConst.USER_CREATED)
    		    .data(resource)
    		    .metadata(null) // Não há paginação de dados aqui
    		    .build();
    	
    	// Adiciona link global HATOAS (opcional)
	    responseBody.add(linkTo(methodOn(UserResource.class)
	    		.findByUserName(dto.getUserName())).withRel("user-by-username").withType(HttpMethod.GET.toString()));
			
    	return ResponseEntity.created(uri).body(responseBody);
	}
	
	@GetMapping
	public ResponseEntity<ResourceHatoasResponse<List<EntityModel<UserResponse>>>> listAllPageable(
			@PageableDefault(
					page = 0, 
					size = Const.PAGE_SIZE, 
					sort = "name", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		
		// Exemplos para montar a uri deste recurso:
		//	  /api/${artifactId}/users
		//	  /api/${artifactId}/users?page=0&size=20
		//    /api/${artifactId}/users?page=0&size=20&sort=username
		//    /api/${artifactId}/users?page=5&size=20&sort=username,desc
		//    /api/${artifactId}/users?page=10&size=20&sort=username,asc&sort=name,desc
		
		// Executa o caso de uso Listar todos os Usuários
		Page<UserOutput> outputs = listAllUsersQuery.list(pageable); // com paginação
		
		// Mapper de outputs para DTOs response para montar o recurso com paginação e links HATOAS
		// Converte usando o MapStruct (que internamente mantém a paginação)
		Page<UserResponse> dtos = mapper.toResponsePage(outputs);
		
		// Monta o corpo da resposta padronizada e com paginação de dados - HATOAS
		ResourceHatoasResponse<List<EntityModel<UserResponse>>> responseBody =
				responseAssembler.toPagedResponse(
						dtos, 
						BusinessConst.USERS_LIST, 
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				
		return ResponseEntity.ok(responseBody);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserResponse>> listAll() {
		Pageable unPaged = Pageable.unpaged(); // sem paginação
		
		// Executa o caso de uso Listar todos os Usuários
		// Uso de getContent() para extrair a lista List<T> do objeto Page<T>, que é retornado pelo Caso de Uso
		List<UserOutput> outputs = listAllUsersQuery.list(unPaged).getContent(); // Listar todos sem paginação
		
		// Mapper de outputs para DTOs response para montar o recurso (sem HATOAS)
		List<UserResponse> responseBody = mapper.toDtoFromApplicationOutputList(outputs);
				
		return ResponseEntity.ok(responseBody);
	}
		
	@GetMapping("/{id}")
    public ResponseEntity<ResourceHatoasResponse<EntityModel<UserResponse>>> findById(@PathVariable String id) {
        // Executa o caso de uso Listar Usuário pelo Id
		UserOutput output = findUserByIdUseCase.execute(id);
        
		// Mapper de output para DTO response para montar o recurso com HATOAS
		UserResponse dto = mapper.toDtoFromApplicationOutput(output);
        
        // Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<UserResponse> resource = hatoasAssembler.toModel(dto);
    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<UserResponse>> responseBody = ResourceHatoasResponse.<EntityModel<UserResponse>>builder()
    		    .status(HttpStatus.OK.getReasonPhrase())
    		    .message(BusinessConst.USER_BY_ID)
    		    .data(resource)
    		    .metadata(null) // Não há paginação de dados aqui
    		    .build();

        return ResponseEntity.ok(responseBody);
    }
	
	@GetMapping("/username/{username}")
    public ResponseEntity<ResourceHatoasResponse<EntityModel<UserResponse>>> findByUserName(@PathVariable String username) {
        // Executa o caso de uso Listar Usuário pelo Nome
		UserOutput output = findUserByUserNameUseCase.execute(username);
        
		// Mapper de output para DTO response para montar o recurso com HATOAS
		UserResponse dto = mapper.toDtoFromApplicationOutput(output);
        
        // Usa o hatoas assembler para adicionar links ao recurso HATOAS
    	EntityModel<UserResponse> resource = hatoasAssembler.toModel(dto);
    	    	
    	// Monta o corpo da resposta padronizada - HATOAS
    	ResourceHatoasResponse<EntityModel<UserResponse>> responseBody = ResourceHatoasResponse.<EntityModel<UserResponse>>builder()
    		    .status(HttpStatus.OK.getReasonPhrase())
    		    .message(BusinessConst.USER_BY_USER_NAME)
    		    .data(resource)
    		    .metadata(null) // Não há paginação de dados aqui
    		    .build();

        return ResponseEntity.ok(responseBody);
    }
}
