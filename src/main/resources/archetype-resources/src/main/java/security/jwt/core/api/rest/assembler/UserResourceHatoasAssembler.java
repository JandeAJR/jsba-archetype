#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import ${package}.security.jwt.core.api.dto.response.UserResponse;
import ${package}.security.jwt.core.api.rest.UserResource;

/**
 * Classe: UserResourceHatoasAssembler <br>
 * Pacote: ${package}.security.jwt.core.api.rest.assembler
 *
 * <p>Classe Assembler responsável por montar os links Hatoas para o recurso UserResource.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class UserResourceHatoasAssembler 
	implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>>{

	@Override
	public EntityModel<UserResponse> toModel(UserResponse dto) {
		return EntityModel.of(dto,
				linkTo(methodOn(UserResource.class).create(null)).withRel("create-user").withType(HttpMethod.POST.toString()),
				linkTo(methodOn(UserResource.class).listAllPageable(null)).withRel("all-users-pageable").withType(HttpMethod.GET.toString()),
				linkTo(methodOn(UserResource.class).listAll()).withRel("all-users").withType(HttpMethod.GET.toString()),
				linkTo(methodOn(UserResource.class).findById(dto.getId().toString())).withSelfRel().withType(HttpMethod.GET.toString()),
				linkTo(methodOn(UserResource.class).findByUserName(dto.getUserName())).withSelfRel().withType(HttpMethod.GET.toString()));
	}
}
