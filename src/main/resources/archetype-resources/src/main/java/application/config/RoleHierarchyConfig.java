#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import ${package}.application.shared.util.Const;

/**
 * Classe: RoleHierarchyConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável por implementar a configuração de hierarquia entre as ROLES da aplicação. <br>
 * Ao definir a configuração com esse bean, o Spring Security injetará automaticamente nos componentes de autorização. <br>
 * Desta forma, em um endpoint com .hasRole("USER"), um usuário com "ROLE_ADMIN" terá o acesso concedido automaticamente.</p>
 * 
 * <p>Referência: <br>
 * <a>https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html</a></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
public class RoleHierarchyConfig {
    @Bean
    RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()           // O Spring Security insere o prefixo default: ROLE_
	        .role(Const.ROLE_ADMIN).implies(Const.ROLE_BASIC_USER) // ROLE_ADMIN herda todas as permissões de ROLE_BASIC_USER
	        .build();
    }
}
