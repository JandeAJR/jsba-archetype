#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.jwt.core.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ${package}.security.jwt.core.application.usecase.role.output.RoleOutput;
import ${package}.security.jwt.core.domain.model.Role;

/**
 * Interface: ApplicationRoleMapper <br>
 * Pacote: ${package}.security.jwt.core.application.mapper
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
public interface ApplicationRoleMapper {
    // From Output UseCase To Domain (RoleOutput -> Role)
    Role toDomainFromApplicationOutput(RoleOutput roleOutput);
    List<Role> toDomainFromApplicationOutputList(List<RoleOutput> outputs);
}
