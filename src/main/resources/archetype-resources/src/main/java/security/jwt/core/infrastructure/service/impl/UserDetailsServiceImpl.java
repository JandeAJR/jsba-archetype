#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ${package}.application.shared.util.Const;
import ${package}.security.jwt.core.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe: UserDetailsServiceImpl<br>
 * Pacote: ${package}.security.jwt.core.infrastructure.service.impl
 * 
 * <p>Classe responsável por implementar o serviço de detalhes do usuário que carrega as informações a partir do repositório.</br>
 * Implementa a interface {@link UserDetailsService} do Spring Framework Security </p>
 * 
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserJpaRepository jpaRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
    	// Busca o usuário pelo userName no repositório de dados
        return jpaRepository.findByUserName(userName)
        		.orElseThrow(() -> new UsernameNotFoundException(Const.USER_CONTEXT_NOT_FOUND));
    }
}
