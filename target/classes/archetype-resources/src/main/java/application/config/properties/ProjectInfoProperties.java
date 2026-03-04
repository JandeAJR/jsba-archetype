#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Record: ProjectInfoProperties<br>
 * Pacote: ${package}.application.core.config.properties
 *
 * <p>Record para mapear a estrutura das propriedades dos arquivos application.yml relacionadas ao projeto.<br>
 * As propriedades são prefixadas com "project".</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@ConfigurationProperties(prefix = "project")
public record ProjectInfoProperties(
    String name,
    String version,
    String database,
    String profile
) {}
