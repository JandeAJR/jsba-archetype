#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ${package}.security.jwt.core.infrastructure.entity.RefreshTokenEntity;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {
	 Optional<RefreshTokenEntity> findById(String id);
	 
	 @Modifying
     @Query("""
     		UPDATE RefreshTokenEntity t
     		SET t.revoked = true
            WHERE t.userId = :userId
     """)
	 void revokeAllByUserId(UUID userId);
	 
     @Modifying
     @Query("""
     		DELETE FROM RefreshTokenEntity t
            WHERE t.expiresAt < :now
     """)
     void deleteAllExpired(OffsetDateTime now);
}
