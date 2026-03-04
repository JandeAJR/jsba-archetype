#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.config;

import org.springframework.context.annotation.Configuration;

import ${package}.application.shared.util.Const;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Classe: SwaggerConfig<br>
 * Pacote: ${package}.application.core.config
 *
 * <p>Classe responsável pela Configuração do Swagger/OpenAPI para documentação da API REST.<br>
 * Define informações como título, versão, descrição, contato e servidores.<br>
 * Essas informações ajudam desenvolvedores a entenderem e utilizarem a API de forma eficaz.<br>
 * A documentação gerada pode ser acessada via Swagger UI.</p>
 * 
 * <p>Swagger/OpenAPI url:<br>
 * Api docs:   http://localhost:8080/api/${artifactId}/v3/api-docs<br>
 * Swagger UI: http://localhost:8080/api/${artifactId}/swagger-ui/index.html</p>
 *
 * Responsável: Alexandre José da Rocha<br>
 * Desde: 2026-01-28
 */

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "JSBA - Java Spring Boot Application Project REST API",
        version = "v1",
        description = """
            O **JSBA** é um acelerador de projetos (Template) focado em aplicações corporativas de alta escalabilidade. 
            Este projeto consolida as melhores práticas do ecossistema Java, 
            seguindo os princípios de **Clean Architecture**, **RESTful APIs** e **HATEOAS**.
        	Este template para projetos baseados em Spring Boot foi concebido seguindo rigorosamente as boas práticas de arquitetura RESTful, 
        	incorporando princípios sólidos de modelagem de domínio e organização modular. 
        	Sua estrutura foi elaborada com foco em promover alta clareza na definição de responsabilidades, 
        	previsibilidade no comportamento dos serviços expostos e escalabilidade na evolução das funcionalidades. 
        	Além disso, o template busca estabelecer padrões consistentes que facilitem a manutenção, 
        	aprimorem a qualidade do código e garantam maior eficiência no desenvolvimento de soluções corporativas.
        """,
        contact = @Contact(
            name = "Time de Arquitetura",
            email = "arquitetura@empresa.com",
            url = "https://empresa.com.br"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8081/api/${artifactId}",
            description = "Ambiente Local"
        ),
        @Server(
            url = "https://empresa.dsv/api/${artifactId}",
            description = "Desenvolvimento"
        ),
        @Server(
            url = "https://empresa.hml/api/${artifactId}",
            description = "Homologação"
        ),
        @Server(
            url = "https://empresa.com.br/api/${artifactId}",
            description = "Produção"
        )
    }
)
@SecurityScheme(
    name = Const.SWAGGER_SECURITY_BEARER_SCHEME_NAME,
    type = SecuritySchemeType.HTTP,
    scheme = Const.SWAGGER_SECURITY_BEARER_SCHEME,
    bearerFormat = Const.SWAGGER_SECURITY_BEARER_SCHEME_FORMAT
)
public class SwaggerConfig {}
