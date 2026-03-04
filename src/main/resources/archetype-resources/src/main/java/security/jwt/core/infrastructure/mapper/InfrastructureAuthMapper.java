#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ${package}.security.jwt.core.api.dto.response.JwtAuthenticatedUserResponse;
import ${package}.security.jwt.core.infrastructure.entity.UserEntity;

/**
 * Interface: InfrastructureAuthMapper <br>
 * Pacote: ${package}.security.jwt.core.infrastructure.mapper
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
public interface InfrastructureAuthMapper {
	// From Entity To DTO (UserEntity -> JwtAuthenticatedUserResponse)
 	@Mapping(source = "username", target = "userName")
 	@Mapping(target = "roles", expression = "java(mapAuthoritiesToStrings(userEntity))")
 	JwtAuthenticatedUserResponse toDtoFromEntity(UserEntity userEntity);
 	
 	default List<String> mapAuthoritiesToStrings(UserEntity userEntity) {
	    if (userEntity.getAuthorities() == null) {
	        return List.of();
	    }
	    
	    return userEntity.getAuthorities()
	    	.stream()
	        .map(auth -> auth.getAuthority()) // converte GrantedAuthority para String
	        .toList();
	}
}
