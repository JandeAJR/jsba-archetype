#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.entity;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: UserEntity<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.entity
 *
 * <p>Entidade JPA Usuário para autenticação e autorização.<br>
 * Implementa {@link UserDetails} do Spring Security.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Entity
@Table(name = "tb_user")
public class UserEntity implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter @Setter
	@JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
	private UUID id;
	
	@Setter
	@Column(name = "user_name", length = 30, unique = true, nullable = false)
	private String userName;
	
	@Getter @Setter
	@Column(name = "email", length = 255, unique = true, nullable = false)
	private String email;
	
	@Getter @Setter
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Setter
	@Column(name = "password", length = 255, nullable = false)
	private String password;
	
	@Setter
	@Column(name = "account_non_expired", nullable = false)
	private Boolean accountNonExpired;
	
	@Setter
	@Column(name = "account_non_locked", nullable = false)
	private Boolean accountNonLocked;
	
	@Setter
	@Column(name = "credencials_non_expired", nullable = false)
	private Boolean credentialsNonExpired;
	
	@Getter @Setter
	@Column(name = "email_verified", nullable = false)
	private Boolean emailVerified;
	
	@Setter
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
			
	@Getter @Setter
	@Column(name = "date_created", nullable = false)
	private OffsetDateTime dateCreated;
	
	@Getter @Setter
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roleEntities;
	
	public UserEntity() {} // Precisa do construtor padrão para o JPA
	
	public UserEntity(
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
			List<RoleEntity> roleEntities) {
		
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.name = name;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.emailVerified = emailVerified;
		this.enabled = enabled;
		this.dateCreated = dateCreated;
		this.roleEntities = roleEntities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Lista as roleEntities de usuário, convertendo para GrantedAuthority do Spring Security
		return roleEntities
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.toList();
	}

	@Override public String getUsername() { 
		return userName; 
	}
	
    @Override public String getPassword() { 
    	return password; 
    }
    
    @Override public boolean isAccountNonExpired() { 
    	return accountNonExpired; 
    }
    
    @Override public boolean isAccountNonLocked() { 
    	return accountNonLocked; 
    }
    
    @Override public boolean isCredentialsNonExpired() { 
    	return credentialsNonExpired; 
    }
    
    @Override public boolean isEnabled() { 
    	return enabled; 
    }
}
