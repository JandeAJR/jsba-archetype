#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.wrapper;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe: ResourceHatoasResponse<br>
 * Pacote: ${package}.application.core.wrapper
 *
 * <p>Classe reponsável por encapsular (wrapper) as respostas http no padrão Hatoas. <br>
 * Utiliza o recurso Hatoas {@link RepresentationModel} do Spring Framewok.</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceHatoasResponse<T> extends RepresentationModel<ResourceHatoasResponse<T>> {
	private String status;
    private String message;
    private T data;
    private Metadata metadata;

	@Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor    
    public static class Metadata {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
    
    /**
     * Métodos hashCode e equal implementados para ficar em conformidades
     * com as regras do SonarLint Rules Descriptions.
     */
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(data);
		return result;
	}

    @Override
    public boolean equals(Object other) {
    	return super.equals(other);
    }
}
