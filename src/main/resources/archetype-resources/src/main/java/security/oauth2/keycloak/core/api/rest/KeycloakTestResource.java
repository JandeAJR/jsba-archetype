#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.application.shared.util.Const;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Classe: KeycloakTestResource <br>
 * Pacote: ${package}.security.oauth2.keycloak.modules.oauth2.api.rest
 *
 * <p>Recurso (controller) REST para testar a autenticação e a autorização de usuários cadastrados no Keycloak.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RestController
@RequestMapping("/keycloak/test/users")
@SecurityRequirement(name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME) // Para aceitar autenticação JWT no Swagger UI
public class KeycloakTestResource {
    @GetMapping
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.status(HttpStatus.OK).body("200 OK - All Users endpoint");
    }
	
    @GetMapping("/admin")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body("200 OK - Admin User endpoint");
    }
    
    @GetMapping("/basic")
    public ResponseEntity<String> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body("200 OK - Basic User endpoint");
    }
    
    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.status(HttpStatus.OK).body("200 OK - Anonymous endpoint");
    }
}
