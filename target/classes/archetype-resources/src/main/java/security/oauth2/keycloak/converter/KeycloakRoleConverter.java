#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import ${package}.application.shared.util.Const;
import lombok.RequiredArgsConstructor;

/**
 * Classe: KeycloakRoleConverter <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.converter
 *
 * <p>Classe Converter para converter as Roles para o padrão estipulado no token JWT emitido pelo Keycloak. <br> 
 * Implementa a interface padrão do Spring Framework {@link Converter}.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RequiredArgsConstructor
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final String clientId;
    private final String customClaimName;

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
    	// Spring Role Prefix: "ROLE_"
    	String springRolePrefix = Const.SPRING_SECURITY_ROLE_PREFIX;
    	
    	// Combina as listas em um Stream para o processamento final
        return Stream.of(extractRealmRoles(jwt),                    // Extrai Roles do Realm (Globais)
                         extractClientRoles(jwt, clientId),         // Extrai Roles do Client (Específicas da Aplicação)
                         extractCustomClaims(jwt, customClaimName)) // Extrai de um campo personalizado (Ex: "authorities")
                .flatMap(List::stream) // Transforma Stream<List<String>> em Stream<String>
                .distinct()
                .map(role -> (role.contains(springRolePrefix) ? "" : springRolePrefix) + role.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()); // O retorno final é List, que é uma Collection
    }

    private List<String> extractRealmRoles(Jwt jwt) {
    	// Extrai Roles do Realm (Globais)
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        return extractRolesFromMap(realmAccess);
    }

    @SuppressWarnings("unchecked")
	private List<String> extractClientRoles(Jwt jwt, String clientId) {
    	// Extrai Roles do Client (Específicas da Aplicação)
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        
        if (resourceAccess != null && resourceAccess.get(clientId) instanceof Map) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
            return extractRolesFromMap(clientAccess);
        }
        
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")    
	private List<String> extractCustomClaims(Jwt jwt, String claimName) {
    	// Extrai de um campo personalizado (Ex: "authorities")
        if (claimName == null || !jwt.hasClaim(claimName)) {
            return Collections.emptyList();
        }
        
        Object claims = jwt.getClaim(claimName);
        
        if (claims instanceof List) {
            return (List<String>) claims;
        } 
        else if (claims instanceof String) {
            return Collections.singletonList((String) claims);
        }
        
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
	private List<String> extractRolesFromMap(Map<String, Object> map) {
    	// Específico para extrair as roles específicas da aplicação
        if (map == null || !(map.get("roles") instanceof List)) {
            return Collections.emptyList();
        }
        
        return (List<String>) map.get("roles");
    }
}
