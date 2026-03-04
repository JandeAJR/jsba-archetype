#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.assembler;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;

import ${package}.application.shared.wrapper.ResourceHatoasResponse;
import lombok.RequiredArgsConstructor;

/**
 * Classe: HatoasResponseAssembler <br>
 * Pacote: ${package}.application.core.assembler
 *
 * <p>Classe reponsável por implementar a montagem da resposta http dos controllers. <br>
 * Monta também a estrutura dos links para paginação de dados. <br>
 * Utiliza os recursos Hatoas {@link EntityModel} e {@link RepresentationModelAssembler} do Spring Framewok.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

@RequiredArgsConstructor
public class HatoasResponseAssembler<T> {
	private final RepresentationModelAssembler<T, EntityModel<T>> itemAssembler;

    public ResourceHatoasResponse<List<EntityModel<T>>> toPagedResponse(
    		Page<T> page, 
    		String message,
    		String baseUrl) {
    	
        List<EntityModel<T>> itemModels = page.getContent()
        		.stream()
                .map(itemAssembler::toModel)
                .toList();

        ResourceHatoasResponse<List<EntityModel<T>>> response = new ResourceHatoasResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setMessage(message);
        response.setData(itemModels);
        response.setMetadata(new ResourceHatoasResponse.Metadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));

        HatoasAssembler.addPaginationLinks(response, page, baseUrl);
        return response;
    }
}
