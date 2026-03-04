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

import ${package}.security.jwt.core.api.dto.response.RoleResponse;
import ${package}.security.jwt.core.api.rest.RoleResource;

/**
 * Classe: RoleResourceHatoasAssembler <br>
 * Pacote: ${package}.security.jwt.core.api.rest.assembler
 *
 * <p>Classe Assembler responsável por montar os links Hatoas para o recurso RoleResource.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class RoleResourceHatoasAssembler 
	implements RepresentationModelAssembler<RoleResponse, EntityModel<RoleResponse>> {

	@Override
	public EntityModel<RoleResponse> toModel(RoleResponse dto) {
		return EntityModel.of(dto,
				linkTo(methodOn(RoleResource.class).create(null)).withRel("create-role").withType(HttpMethod.POST.toString()),
				linkTo(methodOn(RoleResource.class).findAllPageable(null)).withRel("all-roles-pageable").withType(HttpMethod.GET.toString()),
				linkTo(methodOn(RoleResource.class).findAll()).withRel("all-roles").withType(HttpMethod.GET.toString()),
				linkTo(methodOn(RoleResource.class).findById(dto.getId().toString())).withSelfRel().withType(HttpMethod.GET.toString()),
				linkTo(methodOn(RoleResource.class).findByName(dto.getName())).withSelfRel().withType(HttpMethod.GET.toString()));
	}
}
