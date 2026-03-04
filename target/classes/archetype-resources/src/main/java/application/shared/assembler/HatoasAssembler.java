#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.assembler;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ${package}.application.shared.util.Const;

/**
 * Classe: HatoasAssembler <br>
 * Pacote: ${package}.application.core.assembler
 *
 * <p>Classe reponsável por implementar a montagem da estrutura Hatoas para as resposta das requisições http. <br>
 * Monta também a estrutura dos links para paginação de dados, seguindo o padrão Hatoas. <br>
 * Utiliza os recursos Hatoas {@link EntityModel}, {@link Link} e {@link RepresentationModel} do Spring Framewok.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class HatoasAssembler {
	private HatoasAssembler() {
		throw new UnsupportedOperationException(Const.ASSEMBLER_CLASS_CANNOT_BE_INSTANTIATED);
	}
	
	public static <R extends RepresentationModel<?>> void addPaginationLinks(
			R resource, 
			Page<?> page,
			String baseUrl) {
		
		// Cria o UriComponents a partir da URI
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(baseUrl).build();	

		// Recupera o valor do parâmetro "sort" vindo da querystring
		String sortParam = uriComponents.getQueryParams().getFirst("sort");

		// Self
	    String selfPageUrl = UriComponentsBuilder.fromUriString(baseUrl)
	            .replaceQueryParam("page", page.getNumber())
	            .replaceQueryParam("size", page.getSize())
	            .replaceQueryParam("sort", sortParam)
	            .build()
	            .toUriString();
	    resource.add(Link.of(selfPageUrl, "self"));

	    // Next Page
	    if ((page.getTotalPages() - 1) > 0 && page.hasNext()) {
	    	String nextPageUrl = UriComponentsBuilder.fromUriString(baseUrl)
	    			.replaceQueryParam("page", page.getNumber() + 1)
	    			.replaceQueryParam("size", page.getSize())
	    			.replaceQueryParam("sort", sortParam)
	    			.build()
	    			.toUriString();
	    	resource.add(Link.of(nextPageUrl, "next"));	    	
	    }

	    // Previous Page
	    if ((page.getTotalPages() - 1) > 0 && page.hasPrevious()) {
	        String previousPageUrl = UriComponentsBuilder.fromUriString(baseUrl)
	                .replaceQueryParam("page", page.getNumber() - 1)
	                .replaceQueryParam("size", page.getSize())
	                .replaceQueryParam("sort", sortParam)
	                .build()
	                .toUriString();
	        resource.add(Link.of(previousPageUrl, "previous"));
	    }

	    // First Page
	    String firstPageUrl = UriComponentsBuilder.fromUriString(baseUrl)
	            .replaceQueryParam("page", 0)
	            .replaceQueryParam("size", page.getSize())
	            .replaceQueryParam("sort", sortParam)
	            .build()
	            .toUriString();
	    resource.add(Link.of(firstPageUrl, "first"));

	    // Last Page
	    String lastPageUrl = UriComponentsBuilder.fromUriString(baseUrl)
	            .replaceQueryParam("page", page.getTotalPages() - 1)
	            .replaceQueryParam("size", page.getSize())
	            .replaceQueryParam("sort", sortParam)
	            .build()
	            .toUriString();
	    resource.add(Link.of(lastPageUrl, "last"));
    }
}
