#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe: RoleEntity<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.entity
 *
 * <p>Entidade JPA RoleEntity de Usuário para autorização.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Entity
@Table(name = "tb_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@Getter @Setter
	@JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
	private UUID id;	

	@Getter @Setter
	@Column(name = "name", nullable = false, unique = true, length = 50)
    private String name; // Ex: ROLE_ADMIN, ROLE_USER
	
	@Getter @Setter
	@Column(name = "description", nullable = false, unique = true, length = 255)
    private String description;
	
	public RoleEntity() {} // Precisa do construtor padrão para o JPA
	
	public RoleEntity(
			UUID id,
			String name, 
			String description) {
		
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
