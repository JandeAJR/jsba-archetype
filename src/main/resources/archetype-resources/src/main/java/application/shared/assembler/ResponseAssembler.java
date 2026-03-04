#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.assembler;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import ${package}.application.shared.wrapper.ResourceHatoasResponse;

/**
 * Classe: ResponseAssembler <br>
 * Pacote: ${package}.application.core.assembler
 *
 * <p>Classe reponsável por implementar a montagem da resposta http dos controllers. <br>
 * Monta também a estrutura dos links para paginação de dados.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class ResponseAssembler<T> {
	public ResourceHatoasResponse<List<T>> toPagedResponse(
            Page<T> page,
            String message,
            String baseUrl) {

        List<T> items = page.getContent();

        ResourceHatoasResponse<List<T>> response = new ResourceHatoasResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setMessage(message);
        response.setData(items);
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
