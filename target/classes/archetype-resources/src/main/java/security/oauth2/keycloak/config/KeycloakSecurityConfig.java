#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import ${package}.application.shared.exception.CustomAccessDeniedHandler;
import ${package}.application.shared.exception.CustomAuthenticationEntryPoint;
import ${package}.application.shared.exception.global.SecurityConfigurationException;
import ${package}.application.shared.util.Const;
import ${package}.security.common.service.impl.PublicSecurityPolicyServiceImpl;
import ${package}.security.oauth2.keycloak.config.properties.KeycloakProperties;
import ${package}.security.oauth2.keycloak.converter.KeycloakRoleConverter;
import ${package}.security.oauth2.keycloak.core.infrastructure.service.impl.KeycloakSecurityPolicyServiceImpl;
import lombok.RequiredArgsConstructor;

/**
 * Classe: KeycloakSecurityConfig <br>
 * Pacote: ${package}.security.oauth2.keycloak.config
 *
 * <p>Classe reponsável por implementar a configuração de autenticação e autorização EXTERNA à aplicação. <br>
 * Quando toda a implementação de autenticação e autorização é realizada por algum IDM externo à aplicação, 
 * como por exemplo o Keycloak, Authentik, Microsoft Entra ou AWS Cognito. <br>
 * A partir deste cenário, a aplicação segue as regras de autenticação e autorização para os endpoints da sua API. <br>
 * Configura CORS, desabilita CSRF e define quais endpoints são públicos ou protegidos.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ConditionalOnProperty(name = "security.strategy", havingValue = "KEYCLOAK", matchIfMissing = false)
public class KeycloakSecurityConfig {
	private final KeycloakProperties keycloakProperties;
	private final PublicSecurityPolicyServiceImpl publicSecurityPolicyService;
	private final KeycloakSecurityPolicyServiceImpl keycloakSecurityPolicyService;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
	
    @Bean
    SecurityFilterChain externalFilterChain(HttpSecurity http) {
    	// Configura a cadeia de filtros de segurança, definindo as regras de segurança para os endpoints da API.
        try {
        	http.cors(Customizer.withDefaults()) // CORS
            	.csrf(csrf -> csrf.disable())    // API REST -> sem CSRF (Cross-Site Request Forgery)
            	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless

        	// Autorização de requisições, define quais endpoints são públicos e quais exigem autenticação
            .authorizeHttpRequests(auth -> {            
            	publicSecurityPolicyService.configure(auth); // Endpoints públicos
            	keycloakSecurityPolicyService.configure(auth); // Segurança por Roles no Módulo Jwt da aplicação
                auth.anyRequest().authenticated();           // O restante dos endpoints exigem autenticação
            })
            
            // Configuração para tratar exceções falta de autenticação (aqui não existe token) e de acesso negado (http 401 e 403)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(customAuthenticationEntryPoint) // AuthenticationEntryPoint customizado para retornar JSON padrão da aplicação
                .accessDeniedHandler(customAccessDeniedHandler)           // AccessDeniedHandler customizado para retornar JSON padrão da aplicação
            )
            
            // Realiza o tratamento para o token de autenticação retornado pelo IDM
            .oauth2ResourceServer(oauth2 -> oauth2
            	.authenticationEntryPoint(customAuthenticationEntryPoint) // Neste caso o Spring Security valida se          
                .jwt(jwt -> jwt                                          // o token enviado é válido (existe token)
                    .decoder(jwtDecoder())
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );
        	
        	// Retorna a cadeia de filtros de segurança configurada
        	return http.build();        	
        }
    	catch (Exception ex) {
    		throw new SecurityConfigurationException(
    				Const.CONFIGURATION_SECURITY_FILTER_CHAIN_ERROR + " -> " + ex.getMessage(), ex);
    	}
    }
    
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() { // Authorities Converter
    	// Audience (Client_id)      
    	String audience = keycloakProperties.getKeycloak().getClientId();
    	
    	// Verifica se existem valor na propriede claim
    	Boolean claimHasValue = Optional.ofNullable(keycloakProperties.getKeycloak().getClaim())
			    .filter(claim -> claim instanceof String) // Verifica se é String
			    .filter(claim -> !claim.trim().isEmpty()) // Verifica se não é vazio/espaços
			    .isPresent();
    	    	
    	// Se não houver valor para a propriedade claim, pode ser passado null ou uma String vazia (blank)
    	String claim = Boolean.TRUE.equals(claimHasValue) ? keycloakProperties.getKeycloak().getClaim() : null;
    	
    	// Retorna o converter customizado
    	JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();        
        authConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter(audience, claim)); // Aqui é um converter      
        return authConverter;                                                                       // específico para o Keycloak
    }

    @Bean
    OAuth2TokenValidator<Jwt> audienceValidator() { // Audience Validator
    	// Audience == Client_id
        return jwt -> {
            List<String> audience = jwt.getAudience();

            if (audience.contains(keycloakProperties.getKeycloak().getClientId())) {
                return OAuth2TokenValidatorResult.success();
            }

            OAuth2Error error = new OAuth2Error(
                    OAuth2ErrorCodes.INVALID_TOKEN,
                    Const.OAUTH2_INVALID_AUDIENCE_TOKEN,
                    null
            );

            return OAuth2TokenValidatorResult.failure(error);
        };
    }

    @Bean
    JwtDecoder jwtDecoder() { // Jwt Decoder Customizado
        NimbusJwtDecoder decoder =
                (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(keycloakProperties.getKeycloak().getIssuer());

        OAuth2TokenValidator<Jwt> withIssuer =
                JwtValidators.createDefaultWithIssuer(keycloakProperties.getKeycloak().getIssuer());

        OAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(
                        withIssuer,
                        audienceValidator()
                );

        decoder.setJwtValidator(validator);

        return decoder;
    }
}
