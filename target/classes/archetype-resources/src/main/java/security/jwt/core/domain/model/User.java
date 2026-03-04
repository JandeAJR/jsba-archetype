#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: User<br>
 * Pacote: ${package}.security.jwt.core.domain.model
 *
 * <p>Classe de domínio que representa o usuário e as suas regras de negócio.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter @Setter
public class User {	
	private UUID id;
	private String userName;
	private String email;
	private String name;
	private String password;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean emailVerified;
	private Boolean enabled;
	private OffsetDateTime dateCreated;
	private List<Role> roles;
	
	public User(
			UUID id,
			String userName,
			String email,
			String name,
			String password,
			Boolean accountNonExpired, 
			Boolean accountNonLocked,
			Boolean credentialsNonExpired, 
			Boolean emailVerified,
			Boolean enabled,
			OffsetDateTime dateCreated,
			List<Role> roles) {
		
		// Regras para a geração do UUID e de dateCreated
		// Id == NULL -> É um input (request). Deve gerar o UUID para persistir no reposítório de dados.
		// Id != NULL -> É um output (response). O UUID já existe. Ele é retornado pela entidade do repositório de dados.
		// DateCreated == NULL -> É um input (request). Deve gerar a data de criação para persistir no reposítório de dados.
		// DateCreated != NULL -> É um output (response). DateCreated já existe. Ela é retornada pela entidade do repositório de dados.
		this.id = (id == null) ? UUID.randomUUID() : id;
		this.userName = userName;
		this.email = email;
		this.name = name;
		this.password = password;
		this.accountNonExpired = (accountNonExpired == null) ? true : accountNonExpired;             
		this.accountNonLocked = (accountNonLocked == null) ? true : accountNonLocked;               
		this.credentialsNonExpired = (credentialsNonExpired == null) ? true : credentialsNonExpired; 
		this.emailVerified = emailVerified;
		this.enabled = (enabled == null) ? true : enabled;                                      
		this.dateCreated = (dateCreated == null) ? OffsetDateTime.now() : dateCreated;
		this.roles = roles;
	}
}
