#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ${package}.application.shared.exception.CustomAccessDeniedHandler;
import ${package}.application.shared.exception.CustomAuthenticationEntryPoint;
import ${package}.application.shared.exception.global.SecurityConfigurationException;
import ${package}.application.shared.util.Const;
import ${package}.security.common.service.impl.PublicSecurityPolicyServiceImpl;
import ${package}.security.jwt.config.properties.JwtProperties;
import ${package}.security.jwt.core.infrastructure.service.impl.JwtSecurityPolicyServiceImpl;
import ${package}.security.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Classe: JwtSecurityConfig <br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe reponsável por implementar a configuração de autenticação e autorização INTERNA da aplicação. <br>
 * Quando toda a implementação de autenticação e autorização é realizada pela própria aplicação,
 * através de autenticação de usuários internos da aplicação e autorização por roles internas da aplicação. <br>
 * Tudo implementado por token JWT. <br>
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
@ConditionalOnProperty(name = "security.strategy", havingValue = "JWT", matchIfMissing = false)
public class JwtSecurityConfig {
	private final JwtProperties jwtProperties; // Propriedades mapeadas do arquivo application-jwt.yml
	private final PublicSecurityPolicyServiceImpl publicSecurityPolicyService; // Política de seguranção para os endpoints públicos
	private final JwtSecurityPolicyServiceImpl jwtSecurityPolicyService; // Política de segurança para os endpoints privados
	
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
    	// BCrypt é um algoritmo de hashing seguro para senhas
    	// Log_rounds is 12 (cost factor)
    	// https://bcrypt-generator.com
    	// https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/password-encoder.html${symbol_pound}page-title
        return new BCryptPasswordEncoder(jwtProperties.getBcrypt().getStrength().intValue());
    }
    
    @Bean	
    SecurityFilterChain internalSecurityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
    	
    	// Configura a cadeia de filtros de segurança, definindo as regras de segurança para os endpoints da API.
    	try {
            http.cors(Customizer.withDefaults()) // CORS
                .csrf(csrf -> csrf.disable())    // API REST -> sem CSRF (Cross-Site Request Forgery)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless

                // Autorização de requisições, define quais endpoints são públicos e quais exigem autenticação
                .authorizeHttpRequests(auth -> {            
                	publicSecurityPolicyService.configure(auth); // Endpoints públicos
                	jwtSecurityPolicyService.configure(auth);    // Segurança por Roles no Módulo Jwt da aplicação
                    auth.anyRequest().authenticated();           // O restante dos endpoints exigem autenticação
                })

                // Filtro JWT (serve para validar o token em cada requisição)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Sem popup (desabilita autenticação básica e form login)
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                // Erro 401 (unauthorized) para requisições sem token ou token inválido
                // Erro 403 (forbidden) para acesso negado
                .exceptionHandling(ex -> ex
	        		.authenticationEntryPoint(customAuthenticationEntryPoint) // AuthenticationEntryPoint customizado para retornar JSON padrão da aplicação
	        	    .accessDeniedHandler(customAccessDeniedHandler)           // AccessDeniedHandler customizado para retornar JSON padrão da aplicação
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
    AuthenticationManager authenticationManager(
            HttpSecurity http,                      // Configuração HTTP, serve para pegar o AuthenticationManagerBuilder (faz parte do Spring Security)
            UserDetailsService userDetailsService,  // Serviço para carregar detalhes do usuário, serve para autenticação (faz parte do Spring Security)
            PasswordEncoder passwordEncoder) {      // Encoder para senhas, serve para comparar senhas (faz parte do Spring Security)

    	// Metodo para configurar o AuthenticationManager, 
    	// que é responsável por autenticar os usuários com base no UserDetailsService e PasswordEncoder configurados.
    	try {
    		AuthenticationManagerBuilder builder =
    				http.getSharedObject(AuthenticationManagerBuilder.class); // Pega o builder do AuthenticationManager
    		
    		builder
    			.userDetailsService(userDetailsService) // Configura o UserDetailsService para carregar usuários
    			.passwordEncoder(passwordEncoder);      // Configura o PasswordEncoder para comparar senhas
    		
    		// Retorna o AuthenticationManager configurado
    		return builder.build();    		
    	}
    	catch (Exception ex) {
    		throw new SecurityConfigurationException(Const.CONFIGURATION_AUTHENTICATION_MANAGER_ERROR, ex);
    	}
    }
}
