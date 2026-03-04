#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.core.api.mapper;

import org.mapstruct.Mapper;

import ${package}.security.common.domain.model.AuthenticatedUser;
import ${package}.security.oauth2.keycloak.core.api.dto.response.KeycloakAuthenticatedUserResponse;

/**
 * Interface: ApiAuthenticatedUserMapper <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.api.mapper
 * 
 * <p>| TABELA PARA REFERÊNCIA: ONDE CRIAR AS INTERFACES MAPPER (DE PARA), DE ACORDO COM O OBJETIVO DO SEU MAPEAMENTO <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de DOMAIN.MODEL para API.DTO fica na CAMADA API <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de APPLICATION.OUTPUT para API.DTO fica na CAMADA API <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de APPLICATION.OUTPUT para DOMAIN.MODEL fica na CAMADA APLICAÇÃO INTERNA <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de INFRASTRUCTURE.ENTITY para API.DTO (Exclusivo para o Spring Security) fica na CAMADA INFRAESTRUTURA INTERNA <br> 
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de INFRASTRUCTURE.ENTITY para DOMAIN.MODEL fica na CAMADA INFRAESTRUTURA INTERNA <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de INFRASTRUCTURE.PROJECTION para APPLICATION.OUTPUT fica na CAMADA INFRAESTRUTURA INTERNA <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | de DOMAIN.MODEL para INFRASTRUCTURE.ENTITY fica na CAMADA INFRAESTRUTURA INTERNA <br>
 * |------------------------------------------------------------------------------------------------------------------<br></p>
 * 
 * <p>| CONVENÇÃO PARA A NOMENCLATURA DAS INTERFACES MAPPERS <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | NOME DA INTERFACE: {Nome da camada da aplicação}{Domínio}Mapper <br>
 * |------------------------------------------------------------------------------------------------------------------<br>
 * | EXEMPLOS: <br>
 * | CAMADA API: ApiUserMapper <br>
 * | CAMADA APPLICATION: ApplicationUserMapper <br>
 * | CAMADA INFRASTRUCTURE: InfrastructureUserMapper <br>
 * | CAMADA INFRASTRUCTURE (Queries): InfrastructureUserRolesQueryMapper <br>
 * |------------------------------------------------------------------------------------------------------------------<br></p>
 *
 * <p>Interface reponsável por expor o mapeamento (Mapper) entre camadas. <br>
 * Utiliza a biblioteca MapStruct </p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Mapper(componentModel = "spring")
public interface ApiAuthenticatedUserMapper {
	// From Domain To DTO (AuthenticatedUser -> KeycloakAuthenticatedUserResponse)
	KeycloakAuthenticatedUserResponse toDtoFromDomain(AuthenticatedUser authenticatedUser);
}
