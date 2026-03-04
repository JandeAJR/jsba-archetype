#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.rest.assembler;

import org.springframework.stereotype.Component;

import ${package}.application.shared.assembler.ResponseAssembler;
import ${package}.security.jwt.core.api.dto.response.RoleResponse;

/**
 * Classe: RoleQuerySpecResponseAssembler <br>
 * Pacote: ${package}.security.jwt.core.api.rest.assembler
 *
 * <p>Classe Assembler responsável por montar a resposta do recurso query spec http (controller). <br>
 * Cada resource (controller) query spec deve ter a implementação da sua classe ResponseAssembler 
 * que extende de {@link ResponseAssembler}</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Component
public class RoleQuerySpecResponseAssembler extends ResponseAssembler<RoleResponse> {}
