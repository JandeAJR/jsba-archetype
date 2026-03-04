#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.shared.persistence.specification.jpa;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import ${package}.application.shared.query.filter.OperatorFilter;
import ${package}.application.shared.util.Const;

/**
 * Classe: QuerySpecification <br>
 * Pacote: ${package}.application.shared.persistence.specification.jpa
 *
 * <p>Classe responsável por fornecer especificações para critérioas de consultas de dados para interfaces JPA. <br>
 * Implementa nos seus métodos o contrado da interface funcional {@link Specification} do Spring Boot Data JPA.</p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public class QuerySpecification {
	private QuerySpecification() {
		throw new UnsupportedOperationException(Const.SPECIFICATIONS_CLASS_CANNOT_BE_INSTANTIATED);
	}
	
	// Criteria para Strings
	public static <T> Specification<T> criteriaByString(String field, String value, OperatorFilter operator) {
        return (root, query, builder) -> {
            // Se o valor não foi enviado, ignora este filtro
            if (value == null || value.isBlank()) {
                return null;
            }

            // Se o operador não foi enviado, assume EQUALS como padrão
            OperatorFilter operatorFilter = (operator != null) ? operator : OperatorFilter.EQUALS;
            
            // Converte para minúsculo para garantir busca case-insensitive
            String lowerValue = value.toLowerCase();

            return switch (operatorFilter) {
            	case EQUALS -> builder.equal(
            			builder.lower(root.get(field).as(String.class)), 
            			builder.literal(lowerValue)
            	);
            	
            	case NOT_EQUALS -> builder.notEqual(
            			builder.lower(root.get(field).as(String.class)), 
            			builder.literal(lowerValue)
            	);
            
            	case CONTAINS -> builder.like(
            	    builder.lower(root.get(field).as(String.class)),
            	    builder.literal("%" + lowerValue + "%")
            	);

            	case STARTS_WITH -> builder.like(
            	    builder.lower(root.get(field).as(String.class)),
            	    builder.literal(lowerValue + "%")
            	);

            	case ENDS_WITH -> builder.like(
            	    builder.lower(root.get(field).as(String.class)),
            	    builder.literal("%" + lowerValue)
            	);
            
                default -> throw new IllegalArgumentException(Const.INVALID_OPERATOR + operatorFilter);
            };
        };
    }

    // Criteria para comparações (Números, BigDecimals e Datas)
    public static <T, Y extends Comparable<? super Y>> Specification<T> criteriaByComparator(
            String field, Y value, OperatorFilter operator) {
        
        return (root, query, builder) -> {
            // Se não enviou o valor numérico/data, ignora o filtro
            if (value == null) {
                return null;
            }

            // Se não enviou operador, assume EQUALS
            OperatorFilter operatorFilter = (operator != null) ? operator : OperatorFilter.EQUALS;

            return switch (operatorFilter) {
                case EQUALS -> builder.equal(root.get(field), value);
                case NOT_EQUALS -> builder.notEqual(root.get(field), value);
                case GREATER_THAN -> builder.greaterThan(root.get(field), value);
                case LESS_THAN -> builder.lessThan(root.get(field), value);
                case GREATER_THAN_EQUAL -> builder.greaterThanOrEqualTo(root.get(field), value);
                case LESS_THAN_EQUAL -> builder.lessThanOrEqualTo(root.get(field), value);
                default -> throw new IllegalArgumentException(Const.NUMERIC_OR_DATE_INVALID_OPERATOR + operatorFilter);
            };
        };
    }
    
    // Criteria para lidar com cláusulas IN
    public static <T, Y> Specification<T> criteriaByIn(String field, Collection<Y> values) {
        return (root, query, builder) -> {
            // Se a lista vier nula ou vazia, ignora o filtro
            if (values == null || values.isEmpty()) {
                return null;
            }
            
            // O Hibernate monta automaticamente o: WHERE campo IN (val1, val2, val3)
            return root.get(field).in(values);
        };
    }
}
