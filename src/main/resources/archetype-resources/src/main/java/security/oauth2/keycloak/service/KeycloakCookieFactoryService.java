#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.oauth2.keycloak.service;

import ${package}.security.common.service.CookieFactoryService;

/**
 * Interface: KeycloakCookieFactoryService <br>
 * Pacote: ${package}.security.oauth2.keycloak.core.service
 *
 * <p>Interface para fornecer o contrato de fábrica para criação de cookies de acordo com o profile da aplicação. <br>
 * Estende da interface base {@link CookieFactoryService}.</p>
 * 
 * <p><strong>O profile pode ser: prod, develop, homolog e test.</strong></p>
 *
 * Responsável: Alexandre José da Rocha <br>
 * Desde: 2026-01-28
 */

public interface KeycloakCookieFactoryService extends CookieFactoryService {}
