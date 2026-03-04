#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ${package}.security.jwt.core.infrastructure.entity.UserEntity;
import ${package}.security.jwt.core.infrastructure.projection.UserRolesQueryProjection;

/**
 * Interface: UserJpaRepository<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.repository
 * 
 * <p>Interface JpaRepository reponsável por definir as operações de acesso a dados para a entidade UserEntity.<br>
 * Extende as interfaces {@link JpaRepository} e {@link JpaSpecificationExecutor} do Spring Framework Data Jpa.</p>
 * 
 * <p>É a interface de um repositório Jpa</p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

public interface UserJpaRepository 
		extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
	
	// O atributo userName precisa existir na entidade UserEntity -> findBy{atributo}
	Optional<UserEntity> findByUserName(String userName);
	
	// ListUserRolesQuery (projeção)
	// JPQL Query de usuários e suas roles, com filtro por userName e paginação da dados
	@Query(
		value = """
		   SELECT 
		        u.id AS userId, 
		        u.userName AS userName,
		        u.email AS email, 
		        u.name AS name, 
		        u.enabled AS enabled, 
		        u.dateCreated AS dateCreated,
		        r.name AS roleName, 
		        r.description AS roleDescription
		   FROM UserEntity u
		   JOIN u.roleEntities r
		   WHERE LOWER(u.userName) = LOWER(:userName)
		""",
		countQuery = """
		   SELECT count(u) 
		   FROM UserEntity u 
		   JOIN u.roleEntities r 
		   WHERE LOWER(u.userName) = LOWER(:userName)
		"""
	)
	Page<UserRolesQueryProjection> selectUserRolesWithJpqlQuery(@Param("userName") String userName, Pageable pageable);
	
	// ListUserRolesQuery (projeção)
	// Native Query de usuários e suas roles, com filtro por userName e paginação da dados
	@Query(
		value = """
			SELECT
		        u.id AS userId,
		        u.user_name AS userName,
		        u.email AS email,
		        u.name AS name,
		        u.enabled AS enabled,
		        u.date_created AS dateCreated,
				r.name AS roleName,
		        r.description AS roleDescription
		    FROM
		        tb_user u 
		    INNER JOIN
		        tb_user_role ur 
		            on u.id = ur.user_id 
		    INNER JOIN
		        tb_role r 
		            on r.id = ur.role_id 
		    WHERE
		        LOWER(u.user_name) LIKE LOWER(:userName)
		""",
		countQuery = """
		   SELECT count(u) 
		   FROM
		       tb_user u 
		   INNER JOIN
		       tb_user_role ur 
		           on u.id = ur.user_id 
		   INNER JOIN
		       tb_role r 
		           on r.id = ur.role_id 
		   WHERE
		       LOWER(u.user_name) LIKE LOWER(:userName)
		""",
		nativeQuery = true
	)
	Page<UserRolesQueryProjection> selectUserRolesWithNativeQuery(@Param("userName") String userName, Pageable pageable);
}
