#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.dsl;

import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import lombok.RequiredArgsConstructor;

/**
 * Classe: SecurityPolicyDsl <br>
 * Pacote: ${package}.security.common
 *
 * <p>Classe responsável por fornecer uma DSL (Domain-Specific Language) para
 * declarar políticas de segurança de forma fluente, simples e padronizada.</p>
 *
 * <p>Módulo: Núcleo de Segurança <br>
 * Esta classe é utilizada pelos módulos de autenticação (Jwt e OAuth2/Keycloak)
 * para definir regras de autorização de endpoints da API, unificando a forma como
 * a aplicação registra permissões, roles, authorities e padrões de URL.</p>
 *
 * <p><strong>ATENÇÃO:</strong><br>
 * <strong>Esta classe pode ser utilizada  por todas as implementações de política de segurança dos módulos,
 * garantindo padrão, legibilidade e prevenção de erros comuns 
 * (como paths sem '/', má utilização de roles/authorities e inconsistências de configuração).</strong></p>
 * 
 * <p>Endpoints (por domínio) permitidos apenas para ROLES específicas <br>
 * Atenção às especificações do Spring Security: <br>
 *     .hasRole("ADMIN")      -> O Spring Security adiciona o prefixo "ROLE_", ficando igual a "ROLE_ADMIN" <br>
 *     .hasAuthority("ADMIN") -> O Spring Security não adiciona nenhum prefixo, ficando iqual a "ADMIN"</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RequiredArgsConstructor
public class SecurityPolicyDsl {	
    private final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth;

    // ---------- Métodos para endpoints "padrão Spring Security" (Ant pattern) ----------

    public HttpMethodStep get(String pattern) {
        return new HttpMethodStep(HttpMethod.GET, normalizePattern(pattern), auth);
    }

    public HttpMethodStep post(String pattern) {
        return new HttpMethodStep(HttpMethod.POST, normalizePattern(pattern), auth);
    }

    public HttpMethodStep put(String pattern) {
        return new HttpMethodStep(HttpMethod.PUT, normalizePattern(pattern), auth);
    }

    public HttpMethodStep delete(String pattern) {
        return new HttpMethodStep(HttpMethod.DELETE, normalizePattern(pattern), auth);
    }

    public HttpMethodStep patch(String pattern) {
        return new HttpMethodStep(HttpMethod.PATCH, normalizePattern(pattern), auth);
    }
    
    public HttpMethodStep options(String pattern) {
        return new HttpMethodStep(HttpMethod.OPTIONS, normalizePattern(pattern), auth);
    }

    // ---------- Métodos para endpoints com Regex ----------

    public RegexStep regexGet(String regex) {
        return new RegexStep(regex, HttpMethod.GET, auth);
    }

    public RegexStep regexPost(String regex) {
        return new RegexStep(regex, HttpMethod.POST, auth);
    }
    
    public RegexStep regexPut(String regex) {
        return new RegexStep(regex, HttpMethod.PUT, auth);
    }
    
    public RegexStep regexDelete(String regex) {
        return new RegexStep(regex, HttpMethod.DELETE, auth);
    }
    
    public RegexStep regexPatch(String regex) {
        return new RegexStep(regex, HttpMethod.PATCH, auth);
    }
    
    public RegexStep regexOptions(String regex) {
        return new RegexStep(regex, HttpMethod.OPTIONS, auth);
    }

    // ---------- Normalização de pattern para evitar erro "pattern must start with a /" ----------

    private String normalizePattern(String pattern) {
        Objects.requireNonNull(pattern, "pattern não pode ser null");
        
        if (!pattern.startsWith("/")) {
            return "/" + pattern;
        }
        
        return pattern;
    }

    // ---------- Steps internos do DSL ----------

    @RequiredArgsConstructor
    public static class HttpMethodStep {
        private final HttpMethod method;
        private final String pattern;
        private final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth;

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
        roles(String... roles) {
            return auth
                .requestMatchers(method, pattern)
                .hasAnyRole(roles);
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry authorities(String... authorities) {
        	
            return auth
                .requestMatchers(method, pattern)
                .hasAnyAuthority(authorities);
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry permitAll() {
        	
            return auth
                .requestMatchers(method, pattern)
                .permitAll();
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry authenticated() {
        	
            return auth
                .requestMatchers(method, pattern)
                .authenticated();
        }
    }

    @RequiredArgsConstructor
    public static class RegexStep {
        private final String regex;
        private final HttpMethod method;
        private final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth;

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry roles(String... roles) {
        	
            return auth
                .requestMatchers(new RegexRequestMatcher(regex, method.name()))
                .hasAnyRole(roles);
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry authorities(String... authorities) {
        	
            return auth
                .requestMatchers(new RegexRequestMatcher(regex, method.name()))
                .hasAnyAuthority(authorities);
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry permitAll() {
        	
            return auth
                .requestMatchers(new RegexRequestMatcher(regex, method.name()))
                .permitAll();
        }

        public AuthorizeHttpRequestsConfigurer<HttpSecurity>
        		.AuthorizationManagerRequestMatcherRegistry authenticated() {
        	
            return auth
                .requestMatchers(new RegexRequestMatcher(regex, method.name()))
                .authenticated();
        }
    }
}
