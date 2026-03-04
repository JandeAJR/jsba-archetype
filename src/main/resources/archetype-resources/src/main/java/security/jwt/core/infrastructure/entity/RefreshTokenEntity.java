#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe: RefreshTokenEntity<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.entity
 *
 * <p>Entidade JPA RefreshTokenEntity para pesistência do refresh token do usuário.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Entity
@Builder
@Getter @Setter
@Table(name = "tb_refresh_token")
public class RefreshTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name = "user_id", nullable = false)
	@JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
	private UUID userId;
	
	@Column(name = "user_agent_hash", nullable = false)
	private String userAgentHash;
	
	@Column(name = "revoked", nullable = false)
	private Boolean revoked;
	
	@Column(name = "parent_token")
	private String parentToken;
	
	@Column(name = "expires_at", nullable = false)
	private OffsetDateTime expiresAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity userEntity;
	
	public RefreshTokenEntity() {} // Precisa do construtor padrão para o JPA
	
	public RefreshTokenEntity(
			String id, 
			UUID userId, 
			String userAgentHash, 
			Boolean revoked,
			String parentToken,
			OffsetDateTime expiresAt, 
			UserEntity userEntity) {

		this.id = id;
		this.userId = userId;
		this.userAgentHash = userAgentHash;
		this.revoked = revoked;
		this.parentToken = parentToken;
		this.expiresAt = expiresAt;
		this.userEntity = userEntity;
	}
}
