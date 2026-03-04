#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.common.service;

import java.util.List;

import ${package}.security.common.domain.model.AuthenticatedUser;

/**
 * Interface: AuthenticatedUserService <br>
 * Pacote: ${package}.security.common.service
 *
 * <p>Interface para expor o contrato do serviço que irá retornar os dados do usuário autenticado
 * no contexto de segurança da aplicação. O serviço é responsável por fornecer informações 
 * sobre o usuário atualmente autenticado, como seu ID, nome de usuário e outros detalhes relevantes.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface AuthenticatedUserService {
	AuthenticatedUser getCurrentUser();
    String extractUserId();
    String extractUsername();
    String extractEmail();
    String extractName();
    List<String> extractRoles();
}
