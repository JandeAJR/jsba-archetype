#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ${package}.application.shared.assembler.HatoasResponseAssembler;
import ${package}.security.jwt.core.api.dto.response.RoleResponse;

/**
 * Classe: RoleResponseAssembler <br>
 * Pacote: ${package}.security.jwt.core.api.rest.assembler
 *
 * <p>Classe Assembler responsável por montar a resposta do recurso http (controller). <br>
 * Cada resource (controller) deve ter a implementação da sua classe HatoasResponseAssembler
 * que extende de {@link HatoasResponseAssembler}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class RoleResponseAssembler extends HatoasResponseAssembler<RoleResponse> {
	public RoleResponseAssembler(RepresentationModelAssembler<RoleResponse, EntityModel<RoleResponse>> itemAssembler) {
		super(itemAssembler);
	}
}
