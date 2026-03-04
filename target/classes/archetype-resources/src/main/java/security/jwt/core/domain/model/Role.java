#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.domain.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: Role<br>
 * Pacote: ${package}.security.jwt.core.domain.model
 *
 * <p>Classe de domínio que representa a role de usuários e as suas regras de negócio.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
public class Role {	
	private UUID id;
	private String name;
	private String description;
	
	public Role(
			UUID id,
			String name,
			String description) {

		// Regras para a geração do UUID
		// ID == NULL -> É um input (request). Deve gerar o UUID para persistir no reposítório de dados.
		// ID != NULL -> É um output (response). O UUID já existe. Ele é retornado pela entidade do repositório de dados.
		this.id = (id == null) ? UUID.randomUUID() : id;
		this.name = name;
		this.description = description;
	}
}
