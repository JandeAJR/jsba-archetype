#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.wrapper;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe: ResourcePageableResponse<br>
 * Pacote: ${package}.application.core.wrapper
 *
 * <p>Classe reponsável por encapsular (wrapper) as respostas http para paginação de dados.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Getter              
@NoArgsConstructor
public class ResourcePageableResponse<T> {
	private List<T> data;
	private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ResourcePageableResponse(Page<T> pageData) {
        this.data = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
    }
}
