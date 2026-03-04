#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import org.springframework.data.domain.Page;

import ${package}.security.jwt.core.api.dto.response.RoleResponse;
import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.domain.model.Role;

/**
 * Interface: ApiRoleMapper <br>
 * Pacote: ${package}.security.jwt.core.api.mapper
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
public interface ApiRoleMapper {
	// From Domain To DTO (Role -> RoleResponse)
	RoleResponse toDtoFromDomain(Role role);
	
    // From Domain To DTO (List<role.getName()> -> UserResponse.roles)
	// Este método default é utilizado na classe ApiUserMapper (toDtoFromDomain) para mapear 
	// a lista de roles List<Role> para a lista de nomes de roles List<String> usada no Dto.
    default String toString(Role role) {
        if (role == null) {
            return null;
        }
        
        return role.getName();
    }
    
    // From Output UseCase To DTO (RoleOutput -> RoleResponse)
    RoleResponse toDtoFromApplicationOutput(RoleOutput roleOutput);
    List<RoleResponse> toDtoFromApplicationOutputList(List<RoleOutput> outputs);
    
    // Método default para lidar com a paginação.
    default Page<RoleResponse> toResponsePage(Page<RoleOutput> page) {
        if (page == null) {
            return null;
        }
        
        // Usa o método toResponse definido acima
        return page.map(this::toDtoFromApplicationOutput);
    }
}
